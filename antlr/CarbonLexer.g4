lexer grammar CarbonLexer;

WS: [ ] -> skip;
NL: [\r\n];

COMMA: ',';
EQUALS: '=';
COLON: ':';
L_PAREN: '(';
R_PAREN: ')';
L_CURLY: '{';
R_CURLY: '}';

DIGIT : [0-9];
LETTER: [a-zA-Z];
OPERATOR1: '+';
OPERATOR2: '*' | '/';
MINUS: '-';
DOT: '.';

QUOTE_OPEN: '"' -> pushMode(StringLiteral);

mode StringLiteral;
QUOTE_CLOSE: '"' -> popMode;
LITERAL_TEXT: ~('"')+;