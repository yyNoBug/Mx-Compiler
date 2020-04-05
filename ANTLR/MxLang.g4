grammar MxLang;

INT: 'int';
BOOL: 'bool';
STRING: 'string';
NULL: 'null';
VOID: 'void';
TRUE: 'true';
FALSE: 'false';
IF: 'if';
ELSE: 'else';
FOR: 'for';
WHILE: 'while';
BREAK: 'break';
CONTINUE: 'continue';
RETURN: 'return';
NEW: 'new';
CLASS: 'class';
THIS: 'this';

NUM: DIGIT+;
DIGIT: [0-9];
ID: ([a-zA-Z]) ([a-zA-Z] | DIGIT | '_')*;
StringConstant: '"' (~[\u0000-\u001f\\"] | '\\'  [tbnr\\"])* '"';

BLANK: [ \r\t\n] -> skip;
COMMENT1: '//' ~[\r\n]* -> skip;
COMMENT2: '/*' .*? '*/' -> skip;

program: (section)* EOF;
section: classDefinition | functionDefinition | variableDefinition;

classDefinition: 'class' ID '{' (variableDefinition | functionDefinition | classConstructor)* '}'';';
functionDefinition: type ID '(' functionParameterList ')' statementBlock;
variableDefinition: vardeclStatement;

type:
  arrayType
| simpleType;

simpleType: INT | BOOL | VOID | STRING | ID;
arrayType: simpleType (squareBracket)+;

constant: NUM | StringConstant | NULL | TRUE | FALSE;
functionParameterList: (parameterDeclaration (',' parameterDeclaration)*)?;
parameterDeclaration: type ID;

expression:
  expression op = ('++' | '--') # suffixExpr
// | NEW simpleType ('[' expression ']')+ ('[' ']')+ ('[' expression ']')+ # errorNewExpr
| NEW simpleType (squareBracketWithExpression)+ # newExpr
| NEW simpleType ('('')')? # newExpr
// | NEW simpleType '(' (expression (',' expression)* )?  ')' # newExpr
| expression '(' (expression (',' expression)* )? ')' # funCallExpr
// | expression'.'ID '(' (expression (',' expression)* )? ')'  # memberFunCallExpr
| expression'.'ID # memberExpr
| expression'['expression']' # arrayExpr
| '(' expression ')' # parExpr
| <assoc=right> op = ('!' | '+' | '-' | '~') expression # prefixExpr
| <assoc=right> op = ('++' | '--') expression # prefixExpr
| expression op = ('*' | '/' | '%') expression # binaryExpr
| expression op = ('+' | '-') expression # binaryExpr
| expression op = ('<<' | '>>') expression # binaryExpr
| expression op = ('>' | '<' | '>=' | '<=') expression # binaryExpr
| expression op = ('==' | '!=' ) expression # binaryExpr
| expression op = '&' expression # binaryExpr
| expression op = '^' expression # binaryExpr
| expression op = '|' expression # binaryExpr
| expression op = '&&' expression # binaryExpr
| expression op = '||' expression # binaryExpr
// | expression '?'  expression ':' expression
// assignment expressions
| expression '=' expression # assignExpr
| THIS # thisExpr
| ID # idExpr
| constant # constExpr;


// All statement type.
variableDeclarator: ID('=' expression)?;
vardeclStatement: type variableDeclarator (',' variableDeclarator)* ';';
ifStatement: IF '(' expression ')' thenStatement = statement
| IF '(' expression ')' thenStatement = statement ELSE elseStatement = statement;
loopStatement: whileStatement | forStatement;
jmpStatement: breakStatement | continueStatement | returnStatement;
exprStatement: expression';';

breakStatement: BREAK';';
continueStatement: CONTINUE';';
returnStatement: RETURN expression? ';';

statementBlock: '{' statement* '}';

squareBracket: '['']';
squareBracketWithExpression: '[' expression? ']';


whileStatement: WHILE '(' expression ')' statement;
forStatement: FOR '(' init = expression? ';' condition = expression? ';'step =  expression? ')' statement;

classConstructor: ID '('')' statementBlock;

blankStatement: ';';

statement:
  exprStatement
| vardeclStatement
| ifStatement
| loopStatement
| jmpStatement
| blankStatement
| statementBlock;