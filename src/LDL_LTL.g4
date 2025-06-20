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
