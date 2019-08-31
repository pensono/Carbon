grammar Carbon;

WS: [ \n\t\r]+ -> skip;

expression
    : '{' expressionBody '}'
    | numberLiteral
    | identifier argumentList?
    ;

expressionBody
    : definition*
    ;

definition
    : identifier parameterList? '=' expression
    | parameter
    ;

argumentList
    : '(' expression (',' expression)* ')'
    ;

parameterList
    : '(' parameter (',' parameter)* ')'
    ;

parameter : expression (':' expression)?;

numberLiteral
    : DIGIT+
    ;

identifier
    : LETTER (DIGIT | LETTER)+
    ;

DIGIT : [0-9];
LETTER: [a-zA-Z];
