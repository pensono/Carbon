grammar Carbon;

WS: [ \n\t\r]+ -> skip;

expression
    : '{' expressionBody '}'
    | numberLiteral
    ;

expressionBody
    : (identifier '=' expression)*
    ;

numberLiteral
    : DIGIT+
    ;

identifier
    : LETTER (DIGIT | LETTER)+
    ;

DIGIT : [0-9];
LETTER: [a-zA-Z];
