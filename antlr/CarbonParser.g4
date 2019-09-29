parser grammar CarbonParser;

options { tokenVocab = CarbonLexer; }

file : NL* expressionBody NL* EOF;

expression
    : L_CURLY NL* expressionBody NL* R_CURLY # CompositeExpr
    | base=expression argumentList # CallExpr
    | lhs=expression operator=OPERATOR2 rhs=expression # OperatorExpr
    | lhs=expression operator=(OPERATOR1 | MINUS) rhs=expression # OperatorExpr
    | base=expression DOT name=identifier # AccessorExpr
    | MINUS? DIGIT+ # NumberLiteral
    | QUOTE_OPEN content=LITERAL_TEXT? QUOTE_CLOSE # StringLiteral
    | identifier # IdentifierExpr
    ;

expressionBody
    : (definitions+=definition (COMMA NL* | NL+)?)*
    ;

definition
    : name=identifier parameterList? EQUALS expression # Declaration
    | param # Parameter
    ;

argumentList
    : L_PAREN parameters+=expression (COMMA parameters+=expression)* R_PAREN
    ;

parameterList
    : L_PAREN parameters+=param (COMMA parameters+=param)* R_PAREN
    ;

param : name=expression (COLON type=expression)?;

identifier
    : LETTER (DIGIT | LETTER)*
    ;