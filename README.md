# LDLTester

**LDLTester** is a temporal tester generator for linear dynamic logic. The generated tester is written in SMV language that is used by NuSMV and nuXmv.

Xiangyu Luo  
College of Computer Science and Technology, Huaqiao University

## 1. An example
### (1) Run the following command:  
`java -jar LDLTester.jar -ldl <((a+b)*;c)*>d`

### (2) The following SMV code will be outputted

--The LDL formula to be verified: <((a + b)* ; c)*>d  
--The LDL formula without DIAMOND operators: <((a + b)* ; c)*>d  
--The tester of <((a + b)* ; c)*>d  
--Output assertion: X1  
--The output variables for 1 principally temporal sub-formula(s):
1. X1: <((a + b)* ; c)*>d

--No.1 sub-tester for <((a + b)* ; c)*>d  
--Output variable: X1  
VAR  
X1 : boolean;  
Y1 : boolean;  
X2 : boolean;  
Y2 : boolean;  
--The Path Grammar of ((a + b)* ; c)*:  
--  Start variable: 1  
--  Variables: 1 2  
--  Productions:  
--    1. 1 -> c
--    2. 1 -> ((b | a) | c).1  
--    3. 1 -> (b | a).2  
--    4. 1 -> empty  
--    5. 2 -> c.1  
--    6. 2 -> c  
TRANS X1 <-> ((((c & next(d)) | (((b | a) | c) & next(X1))) | ((b | a) & next(X2))) | d);  
TRANS X2 <-> ((c & next(X1)) | (c & next(d)));  
TRANS Y1 -> ((((c & next(d)) | (((b | a) | c) & next(Y1))) | ((b | a) & next(Y2))) | d);  
TRANS Y2 -> ((c & next(Y1)) | (c & next(d)));  
JUSTICE (X1=Y1) & (X2=Y2);  
JUSTICE !Y1 & !Y2;  

## 2. The LDL Syntax (ANTLR 4)

grammar LDL;

ldl : ldlFormula EOF;

### ldlFormula:  
op=PAREN_OPEN ldlFormula PAREN_CLOSE  
| op=NOT ldlFormula  
| ldlFormula op=(AND|OR) ldlFormula  
| ldlFormula op=(IMPLY|BIIMPLY) ldlFormula  
| op=ANGLE_OPEN pathExpr ANGLE_CLOSE ldlFormula  
| op=SQUARE_OPEN pathExpr SQUARE_CLOSE ldlFormula  
| atomicFormula;

### pathExpr:  
pathExpr op=STAR  
| pathExpr op=(SEMI|PLUS) pathExpr  
| op=PAREN_OPEN pathExpr PAREN_CLOSE  
| ldlFormula op=QUESTION  
| propFormula;

### propFormula: 
op=PAREN_OPEN propFormula PAREN_CLOSE  
| op=NOT propFormula  
| propFormula op=(AND|OR) propFormula  
| propFormula op=(IMPLY|BIIMPLY) propFormula  
| atomicFormula;

### atomicFormula:  
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

