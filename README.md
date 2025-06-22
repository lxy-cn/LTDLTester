# LDLTester

**LDLTester** is a temporal tester generator for Linear Dynamic Logic (LDL), which is extended with LTL temporal operators. The generated tester is written in SMV language that is used by NuSMV and nuXmv.

Xiangyu Luo  
College of Computer Science and Technology, Huaqiao University

## 1. Release notes

- **Current version 1.1 (June 22, 2025)**
  - Support tester construction for an extension of LDL with LTL temporal operators. The constructed tester is expressed in the SMV language used by nuXmv and NuSMV. One boolean output variable is built for each LTL temporal operator.
- **Current version 1.0 (June 15, 2025)**
  - Support tester construction for full LDL. The constructed tester is expressed in the SMV language used by nuXmv and NuSMV.
  - When constructing path grammar, do NOT build productions of the form v->a.f? or v->f?.a. 
  - When constructing transition relation, create one boolean variable for each variable in a path grammar.

## 2. Examples
### 2.1 An example for LDL without test
#### (1) Run the following command:  
- `java -jar LDLTester.jar -ldl <((a+b)*;c)*>d` or
- `java -jar LDLTester.jar -file filename` where `filename` is the name of a file that contains the LDL formulas to be handled. One can refer to the file `ldlFormulas.txt` in the `test` directory for the writting format of this file.

#### (2) The following SMV code will be outputted
```
--The original LDL formula: <((a + b)* ; c)*>d
--The LTL formula to be verified: LTLSPEC X1;
--The following SMV code is the tester for the LDL formula without [] operator: <((a + b)* ; c)*>d

--The output variables for 1 principally temporal sub-formula(s):
--  (1) X1: <((a + b)* ; c)*>d

--------- No.1 sub-tester for <((a + b)* ; c)*>d ---------
VAR X1 : boolean;	Y1 : boolean; -- X1 is the output variable
    X2 : boolean;	Y2 : boolean;
--The Path Grammar of ((a + b)* ; c)*:
--  Start variable: 1
--  Variables: [1, 2]
--  Productions:
--    (1) 1 --> empty
--    (2) 1 --> 2
--    (3) 1 --> (a | b) 1
--    (4) 1 --> (a | b) 2
--    (5) 2 --> c
--    (6) 2 --> c 1
TRANS X1 <-> (((d | X2) | ((a | b) & next(X1))) | ((a | b) & next(X2)));
TRANS X2 <-> ((c & next(d)) | (c & next(X1)));
TRANS Y1 -> (((d | Y2) | ((a | b) & next(Y1))) | ((a | b) & next(Y2)));
TRANS Y2 -> ((c & next(d)) | (c & next(Y1)));
JUSTICE X1=Y1 & X2=Y2;
JUSTICE !Y1 & !Y2;
```
#### (3) LDL model checking by NuSMV or nuXmv

- Insert the above generated SMV code into the main module of the SMV model to be verified. 
- Take the output assertion `X1` of this tester as an LTL specification and add it to the SMV model. 
- The model checking result of the LTL specification `X1` exactly is the result of the original LDL specification `<((a + b)* ; c)*>d`.


### 2.2 Another example for LDL with LTL test

Assume that there is a program `while (F b) do { a then b }; c`, where `F b` is an LTL sub-formula denoting that `b` will finally be true. This program can be expressed in the LDL formula `[((F b)? ; a ; b)* ; (!F b)?] c`, whose tester is generated as the following SMV code.

```
--The original LDL formula: [(((F b)? ; a) ; b)* ; (!F b)?]c
--The LTL formula to be verified: LTLSPEC !X1;
--The following SMV code is the tester for the LDL formula without [] operator: !<(((F b)? ; a) ; b)* ; (!F b)?>!c

--The output variables for 2 principally temporal sub-formula(s):
--  (1) W1: F b
--  (2) X1: <(((F b)? ; a) ; b)* ; (!F b)?>!c

--------- No.1 sub-tester for F b ---------
VAR W1 : boolean;
TRANS W1 <-> (b | next(W1));
JUSTICE W1 -> b;

--------- No.2 sub-tester for <(((F b)? ; a) ; b)* ; (!F b)?>!c ---------
VAR X1 : boolean;	Y1 : boolean; -- X1 is the output variable
    X2 : boolean;	Y2 : boolean;
    X3 : boolean;	Y3 : boolean;
    X4 : boolean;	Y4 : boolean;
--The Path Grammar of (((F b)? ; a) ; b)* ; (!F b)?:
--  Start variable: 1
--  Variables: [1, 2, 3, 4]
--  Productions:
--    (1) 1 --> 2
--    (2) 1 --> (F b)? 3
--    (3) 2 --> (!F b)?
--    (4) 3 --> a 4
--    (5) 4 --> b 1
--    (6) 4 --> b 2
TRANS X1 <-> (X2 | (W1 & X3));
TRANS X2 <-> (!W1 & !c);
TRANS X3 <-> (a & next(X4));
TRANS X4 <-> ((b & next(X1)) | (b & next(X2)));
TRANS Y1 -> (Y2 | (W1 & Y3));
TRANS Y2 -> (!W1 & !c);
TRANS Y3 -> (a & next(Y4));
TRANS Y4 -> ((b & next(Y1)) | (b & next(Y2)));
JUSTICE ((X1=Y1 & X2=Y2) & X3=Y3) & X4=Y4;
JUSTICE ((!Y1 & !Y2) & !Y3) & !Y4;
```

## 3. The LDL_LTL Syntax (ANTLR 4)
```antlrv4
grammar LDL_LTL;

ldl : ldlFormula EOF;

ldlFormula :
    op=PAREN_OPEN ldlFormula PAREN_CLOSE # PAREN_LDL
    | atomicFormula # ATOM_LDL
    | op=NOT ldlFormula # NOT_LDL
    | op=(NEXT|PREV|GLOBALLY|FINALLY) ldlFormula # UNARY_LTL_OPTR_LDL
    | ldlFormula op=(UNTIL|RELEASE) ldlFormula # BINARY_LTL_OPTR_LDL
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
// LTL operators
NEXT : 'X' ;
PREV : 'Y' ;
GLOBALLY : 'G' ;
FINALLY : 'F' ;
UNTIL : 'U' ;
RELEASE : 'R' ;

Identifier : [a-zA-Z_][a-zA-Z_0-9]* ;
StringExpr : '\'' .*? '\'' ;

WS : [ \t\r\n]+ -> skip ;
```
