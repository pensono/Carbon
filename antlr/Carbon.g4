grammar Carbon;

WS: [ \n\t\r]+ -> skip;

expression
    : '{' expressionBody '}'
    | numberLiteral
    | identifier argumentList?
    | expression OPERATOR expression
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

numberLiteral
    : DIGIT+
    ;

identifier
    : LETTER (DIGIT | LETTER)+
    ;

DIGIT : [0-9];
LETTER: [a-zA-Z];
OPERATOR: '+' | '-' | '*' | '/';