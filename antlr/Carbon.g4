grammar Carbon;

WS: [ ]+ -> channel(HIDDEN);
NL: [\r\n];

file : NL* expressionBody NL* EOF;

expression
    : '{' NL* expressionBody NL* '}' # CompositeExpr
    | DIGIT+ # NumberLiteral
    | identifier # IdentifierExpr
    | base=expression argumentList # CallExpr
    | lhs=expression operator=OPERATOR rhs=expression # OperatorExpr
    | base=expression DOT name=identifier # AccessorExpr
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
OPERATOR: '+' | '-' | '*' | '/';
DOT: '.';