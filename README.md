# LDLTester

**LDLTester** is a temporal tester generator for linear dynamic logic. The generated tester is written in SMV language that is used by NuSMV and nuXmv.

Xiangyu Luo  
College of Computer Science and Technology, Huaqiao University

## 1. An example for LDL without test
### (1) Run the following command:  
`java -jar LDLTester.jar -ldl <((a+b)*;c)*>d`

### (2) The following SMV code will be outputted
```
--The LDL formula to be verified: <((a + b)* ; c)*>d
--The LDL formula without DIAMOND operators: <((a + b)* ; c)*>d
--The tester of <((a + b)* ; c)*>d
--Output assertion: X1
--The output variables for 1 principally temporal sub-formula(s):
--  (1) X1: <((a + b)* ; c)*>d

--------- No.1 sub-tester for <((a + b)* ; c)*>d ---------
--Output variable: X1
VAR
  X1 : boolean;
  Y1 : boolean;
  X2 : boolean;
  Y2 : boolean;

--The Path Grammar of ((a + b)* ; c)*:
--  Start variable: 1
--  Variables: [1, 2]
--  Productions:
--    (1) 1 -> (a | b) 2
--    (2) 1 -> c
--    (3) 1 -> empty
--    (4) 1 -> ((a | b) | c) 1
--    (5) 2 -> c
--    (6) 2 -> c 1

TRANS X1 <-> (((((a | b) & next(X2)) | (c & next(d))) | d) | (((a | b) | c) & next(X1)));
TRANS X2 <-> ((c & next(d)) | (c & next(X1)));

TRANS Y1 -> (((((a | b) & next(Y2)) | (c & next(d))) | d) | (((a | b) | c) & next(Y1)));
TRANS Y2 -> ((c & next(d)) | (c & next(Y1)));

JUSTICE X1=Y1 & X2=Y2;
JUSTICE !Y1 & !Y2;
```
### (3) The following SMV code will be outputted


## 2. Another example for LDL with test
```
--The LDL formula to be verified: [(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?]c
--The LDL formula without DIAMOND operators: !<(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?>!c
--The tester of !<(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?>!c
--Output assertion: !X1
--The output variables for 2 principally temporal sub-formula(s):
--  (1) X5: <TRUE*>b
--  (2) X1: <(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?>!c

--------- No.1 sub-tester for <TRUE*>b ---------
--Output variable: X5
VAR
X5 : boolean;
Y5 : boolean;

--The Path Grammar of TRUE*:
--  Start variable: 5
--  Variables: [5]
--  Productions:
--    (1) 5 -> empty
--    (2) 5 -> TRUE 5
--    (3) 5 -> TRUE

TRANS X5 <-> ((b | (TRUE & next(X5))) | (TRUE & next(b)));

TRANS Y5 -> ((b | (TRUE & next(Y5))) | (TRUE & next(b)));

JUSTICE X5=Y5;
JUSTICE !Y5;

--------- No.2 sub-tester for <(((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?>!c ---------
--Output variable: X1
VAR
X1 : boolean;
Y1 : boolean;
X2 : boolean;
Y2 : boolean;
X3 : boolean;
Y3 : boolean;
X4 : boolean;
Y4 : boolean;

--The Path Grammar of (((<TRUE*>b)? ; a) ; b)* ; (!<TRUE*>b)?:
--  Start variable: 1
--  Variables: [1, 2, 3, 4]
--  Productions:
--    (1) 1 -> (!<TRUE*>b)?
--    (2) 1 -> (<TRUE*>b)? 2
--    (3) 2 -> a 3
--    (4) 3 -> b 4
--    (5) 3 -> b 1
--    (6) 4 -> (!<TRUE*>b)?

TRANS X1 <-> ((!X5 & !c) | (X5 & X2));
TRANS X2 <-> (a & next(X3));
TRANS X3 <-> ((b & next(X4)) | (b & next(X1)));
TRANS X4 <-> (!X5 & !c);

TRANS Y1 -> ((!Y5 & !c) | (Y5 & Y2));
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
