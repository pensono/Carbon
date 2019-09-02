grammar Carbon;

WS: [ ]+ -> channel(HIDDEN);
NL: [\r\n];

file : NL* expressionBody NL* EOF;

expression
    : '{' NL* expressionBody NL* '}' # CompositeExpr
    | base=expression argumentList # CallExpr
    | lhs=expression operator=operator1 rhs=expression # OperatorExpr
    | base=expression DOT name=identifier # AccessorExpr
    | MINUS? DIGIT+ # NumberLiteral
    | identifier # IdentifierExpr
    ;

expressionBody
    : (definitions+=definition (',' NL* | NL+)?)*
    ;

definition
    : name=identifier parameterList? '=' expression # Declaration
    | param # Parameter
    ;

argumentList
    : '(' parameters+=expression (',' parameters+=expression)* ')'
    ;

parameterList
    : '(' parameters+=param (',' parameters+=param)* ')'
    ;

param : name=expression (':' type=expression)?;

identifier
    : LETTER (DIGIT | LETTER)*
    ;

DIGIT : [0-9];
LETTER: [a-zA-Z];
operator1: '+' | MINUS | '*' | '/';
MINUS: '-';
DOT: '.';