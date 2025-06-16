# LDLTester

**LDLTester** is a temporal tester generator for Linear Dynamic Logic (LDL). The generated tester is written in SMV language that is used by NuSMV and nuXmv.

Xiangyu Luo  
College of Computer Science and Technology, Huaqiao University

## 1. Release notes

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
--Output variable: X1
VAR
  X1 : boolean;		Y1 : boolean;
  X2 : boolean;		Y2 : boolean;

--The Path Grammar of ((a + b)* ; c)*:
--  Start variable: 1
--  Variables: [1, 2]
--  Productions:
--    (1) 1 --> (a | b) 2
--    (2) 1 --> c
--    (3) 1 --> empty
--    (4) 1 --> ((a | b) | c) 1
--    (5) 2 --> c
--    (6) 2 --> c 1

TRANS X1 <-> (((((a | b) & next(X2)) | (c & next(d))) | d) | (((a | b) | c) & next(X1)));
TRANS X2 <-> ((c & next(d)) | (c & next(X1)));

TRANS Y1 -> (((((a | b) & next(Y2)) | (c & next(d))) | d) | (((a | b) | c) & next(Y1)));
TRANS Y2 -> ((c & next(d)) | (c & next(Y1)));

JUSTICE X1=Y1 & X2=Y2;
JUSTICE !Y1 & !Y2;
```
#### (3) LDL model checking by NuSMV or nuXmv

- Insert the above generated SMV code into the main module of the SMV model to be verified. 
- Take the output assertion `X1` of this tester as an LTL specification and add it to the SMV model. 
- The model checking result of the LTL specification `X1` exactly is the result of the original LDL specification `<((a + b)* ; c)*>d`.


### 2.2 Another example for LDL with test

Assume that there is a program `while (F b) do { a then b }; c`, where `F b` denotes that `b` will finally be true. This program can be expressed in the LDL formula `[(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?]c`, whose tester is generated as the following SMV code.

```
--The original LDL formula: [(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?]c
--The LTL formula to be verified: LTLSPEC !X1;
--The following SMV code is the tester for the LDL formula without [] operator: !<(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?>!c

--The output variables for 2 principally temporal sub-formula(s):
--  (1) X5: <TRUE*>b
--  (2) X1: <(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?>!c

--------- No.1 sub-tester for <TRUE*>b ---------
--Output variable: X5
VAR
  X5 : boolean;		Y5 : boolean;

--The Path Grammar of TRUE*:
--  Start variable: 5
--  Variables: [5]
--  Productions:
--    (1) 5 --> TRUE
--    (2) 5 --> empty
--    (3) 5 --> TRUE 5

TRANS X5 <-> (((TRUE & next(b)) | b) | (TRUE & next(X5)));

TRANS Y5 -> (((TRUE & next(b)) | b) | (TRUE & next(Y5)));

JUSTICE X5=Y5;
JUSTICE !Y5;

--------- No.2 sub-tester for <(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?>!c ---------
--Output variable: X1
VAR
  X1 : boolean;		Y1 : boolean;
  X2 : boolean;		Y2 : boolean;
  X3 : boolean;		Y3 : boolean;
  X4 : boolean;		Y4 : boolean;

--The Path Grammar of (((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?:
--  Start variable: 1
--  Variables: [1, 2, 3, 4]
--  Productions:
--    (1) 1 --> (<TRUE*>b)? 2
--    (2) 1 --> (!<TRUE*>b)?
--    (3) 2 --> a 3
--    (4) 3 --> b 4
--    (5) 3 --> b 1
--    (6) 4 --> (!<TRUE*>b)?

TRANS X1 <-> ((X5 & X2) | (!X5 & !c));
TRANS X2 <-> (a & next(X3));
TRANS X3 <-> ((b & next(X4)) | (b & next(X1)));
TRANS X4 <-> (!X5 & !c);

TRANS Y1 -> ((Y5 & Y2) | (!Y5 & !c));
TRANS Y2 -> (a & next(Y3));
TRANS Y3 -> ((b & next(Y4)) | (b & next(Y1)));
TRANS Y4 -> (!Y5 & !c);

JUSTICE ((X1=Y1 & X2=Y2) & X3=Y3) & X4=Y4;
JUSTICE ((!Y1 & !Y2) & !Y3) & !Y4;
```

## 3. The LDL Syntax (ANTLR 4)
```antlrv4
grammar LDL;

ldl : ldlFormula EOF;

ldlFormula:  
op=PAREN_OPEN ldlFormula PAREN_CLOSE  
| op=NOT ldlFormula  
| ldlFormula op=(AND|OR) ldlFormula  
| ldlFormula op=(IMPLY|BIIMPLY) ldlFormula  
| op=ANGLE_OPEN pathExpr ANGLE_CLOSE ldlFormula  
| op=SQUARE_OPEN pathExpr SQUARE_CLOSE ldlFormula  
| atomicFormula;

pathExpr:  
pathExpr op=STAR  
| pathExpr op=(SEMI|PLUS) pathExpr  
| op=PAREN_OPEN pathExpr PAREN_CLOSE  
| ldlFormula op=QUESTION  
| propFormula;

propFormula: 
op=PAREN_OPEN propFormula PAREN_CLOSE  
| op=NOT propFormula  
| propFormula op=(AND|OR) propFormula  
| propFormula op=(IMPLY|BIIMPLY) propFormula  
| atomicFormula;

atomicFormula:  
Identifier                               
| StringExpr;

Identifier : [a-zA-Z_][a-zA-Z_0-9]* ;  
StringExpr : '\'' .*? '\'' ;

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

WS : [ \t\r\n]+ -> skip ;
```
