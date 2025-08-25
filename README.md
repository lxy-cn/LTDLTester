# LTDLTester

**LTDLTester** is a temporal tester generator for Linear Temporal Dynamic Logic (LTDL), which is a combination of Linear Dynamic Logic (LDL) and Future and Past Linear Temporal Logic (LTL). The generated tester is written in SMV language that is used by NuSMV and nuXmv.

Xiangyu Luo  
College of Computer Science and Technology, Huaqiao University

## 1. Release notes

- **Current version 1.2 (August 25, 2025)**
    - Support tester construction for an extension of LDL with future and past LTL temporal operators.
    - The path grammar optimization algorithm is upgraded via backward and forward reduction rules, such that more productions and related variables can be reduced.
- **version 1.1 (June 22, 2025)**
  - Support tester construction for an extension of LDL with LTL temporal operators. The constructed tester is expressed in the SMV language used by nuXmv and NuSMV. One boolean output variable is built for each LTL temporal operator.
- **Version 1.0 (June 15, 2025)**
  - Support tester construction for full LDL. The constructed tester is expressed in the SMV language used by nuXmv and NuSMV.
  - When constructing path grammar, do NOT build productions of the form v->a.f? or v->f?.a. 
  - When constructing transition relation, create one boolean variable for each variable in a path grammar.

## 2. Examples
### 2.1 An example for LTDL without test
#### (1) Run the following command:  
- `java -jar LTDLTester.jar -ldl <((a+b)*;c)*>d` or
- `java -jar LTDLTester.jar -file filename` where `filename` is the name of a file that contains the LTDL formulas to be handled. One can refer to the file `ldlFormulas.txt` in the `test` directory for the writting format of this file.

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

--After optimized and renamed:
--The Path Grammar of ((a + b)* ; c)*:
--  Start variable: 1
--  Variables: [1, 2]
--  Productions:
--    (1) 1 --> ε
--    (2) 1 --> 2
--    (3) 1 --> (a | b) 1
--    (4) 1 --> (a | b) 2
--    (5) 2 --> c
--    (6) 2 --> c 1

TRANS X1 <-> (((d | X2) | ((a | b) & next(X1))) | ((b | a) & next(X2)));
TRANS X2 <-> ((c & next(d)) | (c & next(X1)));
TRANS Y1 -> (((d | Y2) | ((a | b) & next(Y1))) | ((b | a) & next(Y2)));
TRANS Y2 -> ((c & next(d)) | (c & next(Y1)));
JUSTICE X1=Y1 & X2=Y2;
JUSTICE !Y1 & !Y2;
```
#### (3) LTDL model checking by NuSMV or nuXmv

- Insert the above generated SMV code into the main module of the SMV model to be verified. 
- Take the output assertion `X1` of this tester as an LTL specification and add it to the SMV model. 
- The model checking result of the LTL specification `X1` exactly is the result of the original LTDL specification `<((a + b)* ; c)*>d`.


### 2.2 Another example for LTDL with LTL tests

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
--The original LTDL formula: [(((!req)? ; true) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*]!error
--The LTL formula to be verified: LTLSPEC !X1;
--The following SMV code is the tester for the LTDL formula without [] operator: !(<(((!req)? ; true) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*>error)

--The output variables for 2 principally temporal sub-formula(s):
--  (1) W1: !error S working
--  (2) X1: <(((!req)? ; true) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*>error

--------- No.1 sub-tester for !error S working ---------
VAR W1 : boolean;
INIT W1 <-> working;
TRANS next(W1) <-> (next(working) | (next(!error) & W1));

--------- No.2 sub-tester for <(((!req)? ; true) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*>error ---------
VAR X1 : boolean;	Y1 : boolean; -- X1 is the output variable
    X2 : boolean;	Y2 : boolean;
    X3 : boolean;	Y3 : boolean;
    X4 : boolean;	Y4 : boolean;
    X5 : boolean;	Y5 : boolean;

--After optimized and renamed:
--The Path Grammar of (((!req)? ; true) + ((((req? ; working) ; Serve) ; !working) ; ((!error S working)? + ((!(!error S working))? ; Rec))))*:
--  Start variable: 1
--  Variables: [1, 2, 3, 4, 5]
--  Productions:
--    (1) 1 --> ε
--    (2) 1 --> (!req & true)
--    (3) 1 --> (!req & true) 1
--    (4) 1 --> (req & working) 2
--    (5) 2 --> Serve 3
--    (6) 3 --> !working 4
--    (7) 4 --> (!error S working)?
--    (8) 4 --> (!(!error S working))? 5
--    (9) 4 --> (!error S working)? 1
--    (10) 5 --> Rec
--    (11) 5 --> Rec 1

TRANS X1 <-> (((error | ((!req & true) & next(error))) | ((!req & true) & next(X1))) | ((req & working) & next(X2)));
TRANS X2 <-> (Serve & next(X3));
TRANS X3 <-> (!working & next(X4));
TRANS X4 <-> (((W1 & error) | (!W1 & X5)) | (W1 & X1));
TRANS X5 <-> ((Rec & next(error)) | (Rec & next(X1)));
TRANS Y1 -> (((error | ((!req & true) & next(error))) | ((!req & true) & next(Y1))) | ((req & working) & next(Y2)));
TRANS Y2 -> (Serve & next(Y3));
TRANS Y3 -> (!working & next(Y4));
TRANS Y4 -> (((W1 & error) | (!W1 & Y5)) | (W1 & Y1));
TRANS Y5 -> ((Rec & next(error)) | (Rec & next(Y1)));
JUSTICE (((X1=Y1 & X2=Y2) & X3=Y3) & X4=Y4) & X5=Y5;
JUSTICE (((!Y1 & !Y2) & !Y3) & !Y4) & !Y5;
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
