# LDLTester

LDLTester is a temporal tester generator for linear dynamic logic. The generated tester is written in SMV language that is used by NuSMV and nuXmv.

Xiangyu Luo

College of Computer Science and Technology, Huaqiao University

# The LDL Syntax

grammar LDL;

ldl : ldlFormula EOF;

ldlFormula: 

op=PAREN_OPEN ldlFormula PAREN_CLOSE

| op=NOT ldlFormula

| ldlFormula op=(AND|OR) ldlFormula

| ldlFormula op=(IMPLY|BIIMPLY) ldlFormula

| op=ANGLE_OPEN pathExpr ANGLE_CLOSE ldlFormula

| op=SQUARE_OPEN pathExpr SQUARE_CLOSE ldlFormula

| atomicFormula                           
;

pathExpr: 

pathExpr op=STAR

| pathExpr op=(SEMI|PLUS) pathExpr

| op=PAREN_OPEN pathExpr PAREN_CLOSE

| ldlFormula op=QUESTION

| propFormula                          
;

propFormula: 

op=PAREN_OPEN propFormula PAREN_CLOSE

| op=NOT propFormula

| propFormula op=(AND|OR) propFormula

| propFormula op=(IMPLY|BIIMPLY) propFormula

| atomicFormula
;

atomicFormula: 

Identifier

| StringExpr
;

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
