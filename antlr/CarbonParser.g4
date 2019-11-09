parser grammar CarbonParser;

options { tokenVocab = CarbonLexer; }

file : NL* expressionBody NL* EOF;

expression
    : L_CURLY NL* expressionBody NL* R_CURLY # CompositeExpr
    | base=expression argumentList # CallExpr
    | lhs=expression operator=OPERATOR3 rhs=expression # OperatorExpr
    | lhs=expression operator=(OPERATOR2 | MINUS) rhs=expression # OperatorExpr
    | lhs=expression operator=OPERATOR1 rhs=expression # OperatorExpr
    | base=expression DOT name=identifier # AccessorExpr
    | MINUS? DIGIT+ # NumberLiteral
    | QUOTE_OPEN content=LITERAL_TEXT? QUOTE_CLOSE # StringLiteral
    | identifier # IdentifierExpr
    ;

expressionBody
    : (definitions+=definition (COMMA NL* | NL+)?)*
    ;

definition
    : (annotations+=annotation NL+)* name=identifier parameterList? NL* (PIPE conditions+=expression NL* EQUALS NL* condition_vals+=expression NL*)* EQUALS NL* body=expression # Declaration
    | param # Parameter
    ;

argumentList
    : L_PAREN NL* parameters+=expression (NL* COMMA NL* parameters+=expression)* NL* R_PAREN
    ;

parameterList
    : L_PAREN NL* parameters+=param (NL* COMMA NL* parameters+=param)* NL* R_PAREN
    ;

param : name=expression (NL* COLON NL* type=expression)?;

annotation
    : OCTOTHORPE expression
    ;

identifier
    : LETTER (DIGIT | LETTER)*
    ;