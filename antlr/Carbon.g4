grammar Carbon;

WS: [ \n\t\r]+ -> skip;

file : expressionBody EOF;

expression
    : '{' expressionBody '}' # CompositeExpr
    | DIGIT+ # NumberLiteral
    | identifier argumentList? # IdentifierExpr
    | lhs=expression operator=OPERATOR rhs=expression # OperatorExpr
    | base=expression DOT name=identifier # AccessorExpr
    ;

expressionBody
    : definitions+=definition*
    ;

definition
    : name=identifier parameterList? '=' expression # Declaration
    | param # Parameter
    ;

argumentList
    : '(' expression (',' expression)* ')'
    ;

parameterList
    : '(' param (',' param)* ')'
    ;

param : expression (':' expression)?;

identifier
    : LETTER (DIGIT | LETTER)*
    ;

DIGIT : [0-9];
LETTER: [a-zA-Z];
OPERATOR: '+' | '-' | '*' | '/';
DOT: '.';