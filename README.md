# LTDLTester

**LTDLTester** is a temporal tester generator for Linear Temporal Dynamic Logic (LTDL), which is a combination of Linear Dynamic Logic (LDL) and Future and Past Linear Temporal Logic (LTL). The generated tester is written in SMV language that is used by NuSMV and nuXmv.


## 1. Release notes
- **Current version 1.3 (March 12, 2026)**
  - Support the elimination of zero-delay cycles within regular grammars.
  - Support BDD-based simplification of the formulas in grammar productions.
- **Current version 1.2 (August 25, 2025)**
    - Support tester construction for LTDL, an extension of LDL with future and past LTL temporal operators.
    - The path grammar optimization algorithm is upgraded via backward and forward reduction rules, such that more productions and related variables can be reduced.
- **version 1.1 (June 22, 2025)**
  - Support tester construction for an extension of LDL with LTL temporal operators. The constructed tester is expressed in the SMV language used by nuXmv and NuSMV. One boolean output variable is built for each LTL temporal operator.
- **Version 1.0 (June 15, 2025)**
  - Support tester construction for full LDL. The constructed tester is expressed in the SMV language used by nuXmv and NuSMV.
  - When constructing path grammar, do NOT build productions of the form v->a.f? or v->f?.a. 
  - When constructing transition relation, create one boolean variable for each variable in a path grammar.

## 2. Examples
### 2.1 The first example for LTDL without test
#### (1) Run the following command:  
`java -jar LTDLTester.jar -file filename` where `filename` is the name of a text file that contains the LTDL formulas to be handled. The writing format of this file is as follows:
- Every LTDL formula can occupy multi lines and must end with '#'
- Every line starting with '--' is commented out.
- Multiline text can be commented out by enclosing it within '/\*' and '\*/'

One can refer to the file `LTDLs.txt` in the `test` directory for the writing format of this file.

#### (2) The following SMV code will be outputted
```
--The original LTDL formula: <((a + b)* ; c)*>d
--The LTL formula to be verified: LTLSPEC X1;
--The following SMV code is the tester for the LTDL formula without [] operator: <((a + b)* ; c)*>d

--The output variables for 1 principally temporal sub-formula(s):
--  (1) X1: <((a + b)* ; c)*>d

--------- No.1 sub-tester for <((a + b)* ; c)*>d ---------
VAR X1 : boolean;	Y1 : boolean; -- X1 is the output variable
    X2 : boolean;	Y2 : boolean;

--Initial Path Grammar:
--The Path Grammar of ((a + b)* ; c)*:
--  Start variable: 3
--  Variables: [1, 2, 3, 4]
--  Productions:
--    (1) 3 --> ε
--    (2) 3 --> 1
--    (3) 3 --> 2
--    (4) 3 --> 4
--    (5) 1 --> a 3
--    (6) 1 --> a 4
--    (7) 2 --> b 3
--    (8) 2 --> b 4
--    (9) 4 --> c
--    (10) 4 --> c 3

--First Optimized Path Grammar:
--The Path Grammar of ((a + b)* ; c)*:
--  Start variable: 3
--  Variables: [3, 4]
--  Productions:
--    (1) 3 --> ε
--    (2) 3 --> 4
--    (3) 3 --> ((!b & a) | b) 3
--    (4) 3 --> ((!b & a) | b) 4
--    (5) 4 --> c
--    (6) 4 --> c 3

--No Change in the Zero-delay Cycles Elimination.
--No Change in the Second Optimization.
--Variable Renamed Path Grammar:
--The Path Grammar of ((a + b)* ; c)*:
--  Start variable: 1
--  Variables: [1, 2]
--  Productions:
--    (1) 1 --> ε
--    (2) 1 --> 2
--    (3) 1 --> ((!b & a) | b) 1
--    (4) 1 --> ((!b & a) | b) 2
--    (5) 2 --> c
--    (6) 2 --> c 1

TRANS X1 <-> (((d | X2) | ((b | (!b & a)) & next(X1))) | ((b | (!b & a)) & next(X2)));
TRANS X2 <-> ((c & next(d)) | (c & next(X1)));
TRANS Y1 -> (((d | Y2) | ((b | (!b & a)) & next(Y1))) | ((b | (!b & a)) & next(Y2)));
TRANS Y2 -> ((c & next(d)) | (c & next(Y1)));
JUSTICE X1=Y1 & X2=Y2;
JUSTICE !Y1 & !Y2;
```
#### (3) LTDL model checking by NuSMV or nuXmv

- Insert the above generated SMV code into the main module of the SMV model to be verified. 
- Take the output assertion `X1` of this tester as an LTL specification and add it to the SMV model. 
- The model checking result of the LTL specification `X1` exactly is the result of the original LTDL specification `<((a + b)* ; c)*>d`.


### 2.2 The second example for LTDL with LTL tests

Suppose there is a network service program that, upon receiving a request signal `req`, executes the service process `Serve`. After completing the service, if an error signal `error` is detected, it executes the recovery process `Rec`; otherwise, it continues to receive request signals. This program can be expressed in the following LTDL formula
```
[( (    req? ;
        working;
        Serve;
        !working;
        (   ((!(!error S working))? ; Rec) +
            (!error S working)?
        )
    ) +
    (!req? ; true)
 )*
] !error
```
where `req`, `working`, and `error` are Boolean variables, and `Serve` and `Rec` can be composite path expressions used to represent the service process and the recovery process, respectively. The test `(!error S working)?` verifies that no error has occurred since the service process started working.

It can be seen that regular expressions can naturally describe program control structures, while testing past-time LTL formulas can conveniently capture the process of a program executing actions based on the evolution of past states. Therefore, LTDL logic is highly suitable for program verification.

The following SMV code is outputted by LTDLTester for the above LTDL formula.

```
--The original LTDL formula: <(((((req? ; working) ; Serve) ; !working) ; (((!(!error S working))? ; Rec) + (!error S working)?)) + ((!req)? ; TRUE))*>!error
--The LTL formula to be verified: LTLSPEC X1;
--The following SMV code is the tester for the LTDL formula without [] operator: <(((!req)? ; TRUE) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*>!error

--The output variables for 2 principally temporal sub-formula(s):
--  (1) W1: !error S working
--  (2) X1: <(((!req)? ; TRUE) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*>!error

--------- No.1 sub-tester for !error S working ---------
VAR W1 : boolean;
INIT W1 <-> working;

--No Change in the First Optimization.
--No Change in the Zero-delay Cycles Elimination.
--No Change in the Second Optimization.

TRANS next(W1) <-> (next(working) | (next(!error) & W1));

--------- No.2 sub-tester for <(((!req)? ; TRUE) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*>!error ---------
VAR X1 : boolean;	Y1 : boolean; -- X1 is the output variable
    X2 : boolean;	Y2 : boolean;
    X3 : boolean;	Y3 : boolean;
    X4 : boolean;	Y4 : boolean;
    X5 : boolean;	Y5 : boolean;

--Initial Path Grammar:
--The Path Grammar of (((!req)? ; TRUE) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*:
--  Start variable: 11
--  Variables: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
--  Productions:
--    (1) 11 --> ε
--    (2) 11 --> 1
--    (3) 11 --> 9
--    (4) 1 --> req? 2
--    (5) 9 --> (!req)? 10
--    (6) 2 --> working 3
--    (7) 10 --> TRUE
--    (8) 10 --> TRUE 11
--    (9) 3 --> Serve 4
--    (10) 4 --> !working 8
--    (11) 8 --> 5
--    (12) 8 --> 7
--    (13) 5 --> (!(!error S working))? 6
--    (14) 7 --> (!error S working)?
--    (15) 7 --> (!error S working)? 11
--    (16) 6 --> Rec
--    (17) 6 --> Rec 11

--First Optimized Path Grammar:
--The Path Grammar of (((!req)? ; TRUE) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*:
--  Start variable: 11
--  Variables: [3, 4, 6, 8, 11]
--  Productions:
--    (1) 11 --> ε
--    (2) 11 --> !req
--    (3) 11 --> !req 11
--    (4) 11 --> (req & working) 3
--    (5) 3 --> Serve 4
--    (6) 4 --> !working 8
--    (7) 8 --> (!error S working)?
--    (8) 8 --> (!(!error S working))? 6
--    (9) 8 --> (!error S working)? 11
--    (10) 6 --> Rec
--    (11) 6 --> Rec 11

--No Change in the Zero-delay Cycles Elimination.
--No Change in the Second Optimization.
--Variable Renamed Path Grammar:
--The Path Grammar of (((!req)? ; TRUE) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*:
--  Start variable: 1
--  Variables: [1, 2, 3, 4, 5]
--  Productions:
--    (1) 1 --> ε
--    (2) 1 --> !req
--    (3) 1 --> !req 1
--    (4) 1 --> (req & working) 2
--    (5) 2 --> Serve 3
--    (6) 3 --> !working 4
--    (7) 4 --> (!error S working)?
--    (8) 4 --> (!(!error S working))? 5
--    (9) 4 --> (!error S working)? 1
--    (10) 5 --> Rec
--    (11) 5 --> Rec 1

TRANS X1 <-> (((!error | (!req & next(!error))) | (!req & next(X1))) | ((req & working) & next(X2)));
TRANS X2 <-> (Serve & next(X3));
TRANS X3 <-> (!working & next(X4));
TRANS X4 <-> (((W1 & !error) | (!W1 & X5)) | (W1 & X1));
TRANS X5 <-> ((Rec & next(!error)) | (Rec & next(X1)));
TRANS Y1 -> (((!error | (!req & next(!error))) | (!req & next(Y1))) | ((req & working) & next(Y2)));
TRANS Y2 -> (Serve & next(Y3));
TRANS Y3 -> (!working & next(Y4));
TRANS Y4 -> (((W1 & !error) | (!W1 & Y5)) | (W1 & Y1));
TRANS Y5 -> ((Rec & next(!error)) | (Rec & next(Y1)));
JUSTICE (((X1=Y1 & X2=Y2) & X3=Y3) & X4=Y4) & X5=Y5;
JUSTICE (((!Y1 & !Y2) & !Y3) & !Y4) & !Y5;
```
### 2.3 The third example for LTDL having zero-delay cycles
The following SMV code is outputted by LTDLTester for the LTDL formula `<(a?+b)*>c`.
```
--The original LTDL formula: <(a? + b)*>c
--The LTL formula to be verified: LTLSPEC X1;
--The following SMV code is the tester for the LTDL formula without [] operator: <(a? + b)*>c

--The output variables for 1 principally temporal sub-formula(s):
--  (1) X1: <(a? + b)*>c

--------- No.1 sub-tester for <(a? + b)*>c ---------
VAR X1 : boolean;	Y1 : boolean; -- X1 is the output variable

--Initial Path Grammar:
--The Path Grammar of (a? + b)*:
--  Start variable: 3
--  Variables: [1, 2, 3]
--  Productions:
--    (1) 3 --> ε
--    (2) 3 --> 1
--    (3) 3 --> 2
--    (4) 1 --> a?
--    (5) 1 --> a? 3
--    (6) 2 --> b
--    (7) 2 --> b 3

--First Optimized Path Grammar:
--The Path Grammar of (a? + b)*:
--  Start variable: 3
--  Variables: [3]
--  Productions:
--    (1) 3 --> ε
--    (2) 3 --> a?
--    (3) 3 --> b
--    (4) 3 --> a? 3
--    (5) 3 --> b 3

--Zero-delay Cycles Eliminated Path Grammar:
--The Path Grammar of (a? + b)*:
--  Start variable: 3
--  Variables: [3]
--  Productions:
--    (1) 3 --> ε
--    (2) 3 --> a?
--    (3) 3 --> (a & b)
--    (4) 3 --> b
--    (5) 3 --> (a & b) 3
--    (6) 3 --> b 3

--Second Optimized Path Grammar:
--The Path Grammar of (a? + b)*:
--  Start variable: 3
--  Variables: [3]
--  Productions:
--    (1) 3 --> ε
--    (2) 3 --> a?
--    (3) 3 --> b
--    (4) 3 --> b 3

--Variable Renamed Path Grammar:
--The Path Grammar of (a? + b)*:
--  Start variable: 1
--  Variables: [1]
--  Productions:
--    (1) 1 --> ε
--    (2) 1 --> a?
--    (3) 1 --> b
--    (4) 1 --> b 1

TRANS X1 <-> (((c | (a & c)) | (b & next(c))) | (b & next(X1)));
TRANS Y1 -> (((c | (a & c)) | (b & next(c))) | (b & next(Y1)));
JUSTICE X1=Y1;
JUSTICE !Y1;
```

### 2.4 The fourth example for LTDL having nested '*' operators and zero-delay cycles
The following SMV code is outputted by LTDLTester for the LTDL formula `<(a?+(b+c?)*)*>d`.

```
--The original LTDL formula: <(a? + (b + c?)*)*>d
--The LTL formula to be verified: LTLSPEC X1;
--The following SMV code is the tester for the LTDL formula without [] operator: <((b + c?)* + a?)*>d

--The output variables for 1 principally temporal sub-formula(s):
--  (1) X1: <((b + c?)* + a?)*>d

--------- No.1 sub-tester for <((b + c?)* + a?)*>d ---------
VAR X1 : boolean;	Y1 : boolean; -- X1 is the output variable

--Initial Path Grammar:
--The Path Grammar of ((b + c?)* + a?)*:
--  Start variable: 5
--  Variables: [1, 2, 3, 4, 5]
--  Productions:
--    (1) 5 --> ε
--    (2) 5 --> 1
--    (3) 5 --> 4
--    (4) 1 --> a?
--    (5) 1 --> a? 5
--    (6) 4 --> ε
--    (7) 4 --> 2
--    (8) 4 --> 3
--    (9) 4 --> 5
--    (10) 2 --> b
--    (11) 2 --> b 4
--    (12) 2 --> b 5
--    (13) 3 --> c?
--    (14) 3 --> c? 4
--    (15) 3 --> c? 5

--First Optimized Path Grammar:
--The Path Grammar of ((b + c?)* + a?)*:
--  Start variable: 5
--  Variables: [4, 5]
--  Productions:
--    (1) 5 --> ε
--    (2) 5 --> a?
--    (3) 5 --> 4
--    (4) 5 --> a? 5
--    (5) 4 --> ε
--    (6) 4 --> c?
--    (7) 4 --> b
--    (8) 4 --> 5
--    (9) 4 --> c? 4
--    (10) 4 --> c? 5
--    (11) 4 --> b 4
--    (12) 4 --> b 5

--Zero-delay Cycles Eliminated Path Grammar:
--The Path Grammar of ((b + c?)* + a?)*:
--  Start variable: 5
--  Variables: [5]
--  Productions:
--    (1) 5 --> ε
--    (2) 5 --> (a & c)?
--    (3) 5 --> a?
--    (4) 5 --> c?
--    (5) 5 --> ((b & c) & a)
--    (6) 5 --> (a & b)
--    (7) 5 --> b
--    (8) 5 --> (b & c)
--    (9) 5 --> ((b & c) & a) 5
--    (10) 5 --> (a & b) 5
--    (11) 5 --> b 5
--    (12) 5 --> (b & c) 5

--Second Optimized Path Grammar:
--The Path Grammar of ((b + c?)* + a?)*:
--  Start variable: 5
--  Variables: [5]
--  Productions:
--    (1) 5 --> ε
--    (2) 5 --> ((!a & c) | a)?
--    (3) 5 --> b
--    (4) 5 --> b 5

--Variable Renamed Path Grammar:
--The Path Grammar of ((b + c?)* + a?)*:
--  Start variable: 1
--  Variables: [1]
--  Productions:
--    (1) 1 --> ε
--    (2) 1 --> ((!a & c) | a)?
--    (3) 1 --> b
--    (4) 1 --> b 1

TRANS X1 <-> (((d | ((a | (!a & c)) & d)) | (b & next(d))) | (b & next(X1)));
TRANS Y1 -> (((d | ((a | (!a & c)) & d)) | (b & next(d))) | (b & next(Y1)));
JUSTICE X1=Y1;
JUSTICE !Y1;
```

## 3. LTDL Syntax (ANTLR 4)
```antlrv4
grammar LTDL;

ldl : ldlFormula EOF; // We are still using LDL instead of LTDL to simplify the grammar.

ldlFormula :
    op=PAREN_OPEN ldlFormula PAREN_CLOSE # PAREN_LDL
    | atomicFormula # ATOM_LDL
    | op=NOT ldlFormula # NOT_LDL
    | op=(NEXT|FINALLY|GLOBALLY|PREVIOUS|PAST|HISTORICALLY) ldlFormula # UNARY_LTL_OPTR_LDL
    | ldlFormula op=(UNTIL|RELEASE|SINCE|TRIGGER) ldlFormula # BINARY_LTL_OPTR_LDL
    | op=ANGLE_OPEN pathExpr ANGLE_CLOSE ldlFormula # DIAMOND_LDL
    | op=SQUARE_OPEN pathExpr SQUARE_CLOSE ldlFormula # BOX_LDL
    | ldlFormula op=(AND|OR|IMPLY|BIIMPLY) ldlFormula # BINARY_BOOL_OPTR_LDL
    ;

pathExpr :
    op=PAREN_OPEN pathExpr PAREN_CLOSE # PAREN_PATHEXPR
    | propFormula # PROP_PATHEXPR
    | pathExpr op=STAR # REPETITION_PATHEXPR
    | ldlFormula op=QUESTION # TEST_PATHEXPR
    | pathExpr op=(SEMI|PLUS) pathExpr # TWO_OPERANDS_PATHEXPR
    ;

propFormula :
    op=PAREN_OPEN propFormula PAREN_CLOSE # PAREN_PROP
    | atomicFormula # ATOM_PROP
    | op=NOT propFormula # NOT_PROP
    | propFormula op=(AND|OR|IMPLY|BIIMPLY) propFormula # TWO_OPERANDS_PROP
    ;

atomicFormula :
    Identifier # ID_ATOM
    | StringExpr # STREXPR_ATOM
    ;

BIIMPLY : '<->';
IMPLY : '->';
AND : '&';
OR  : '|';
NOT : '!';
STAR : '*' ;
PLUS : '+' ;
SEMI : ';' ;
QUESTION : '?' ;
PAREN_OPEN : '(' ;
PAREN_CLOSE : ')' ;
ANGLE_OPEN : '<' ;
ANGLE_CLOSE : '>' ;
SQUARE_OPEN : '[' ;
SQUARE_CLOSE : ']' ;
// future LTL operators
NEXT : 'X' ;
UNTIL : 'U' ;
FINALLY : 'F' ;
GLOBALLY : 'G' ;
RELEASE : 'R' ;
// past LTL operators
PREVIOUS : 'Y' ;
SINCE : 'S' ;
PAST : 'P' ;
HISTORICALLY : 'H' ;
TRIGGER : 'T' ;

Identifier : [a-zA-Z_][a-zA-Z_0-9]* ;
StringExpr : '\'' .*? '\'' ;

WS : [ \t\r\n]+ -> skip ;
```
