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
StringConstant: '"' (~[\u0000~\u001f\\"] | '\\'  [tbnr\\"])* '"';

BLANK: [ \r\t\n] -> skip;
COMMENT1: '//' .*? '\n' -> skip;
COMMENT2: '/*' .*? '*/' -> skip;

program: (section)* EOF;
section: classDefinition | functionDefinition | variableDefinition;

classDefinition: 'class' ID '{' (variableDefinition | functionDefinition | classConstructor)* '}';
functionDefinition: type ID '(' functionParameterList ')' statementBlock;
variableDefinition: vardeclStatement;

type:
  arrayType
| simpleType;

simpleType: INT | BOOL | VOID | STRING | ID;
arrayType: simpleType (squareBracket)+;

squareBracket: '['']';
squareBracketWithExpression: '[' expression? ']';

constant: NUM | StringConstant | NULL | TRUE | FALSE;
functionParameterList: (parameterDeclaration (',' parameterDeclaration)*)?;
parameterDeclaration: type ID;

expression:
// function
  expression '(' (expression (',' expression)* )? ')' # funCallExpr
// | expression'.'ID '(' (expression (',' expression)* )? ')'  # memberFunCallExpr
| '(' expression ')' # parExpr
| <assoc=right> op = ('!' | '+' | '-' | '~') expression # prefixExpr
// suffix
| expression op = ('++' | '--') # suffixExpr
// prefix
| <assoc=right> ('++' | '--') expression # prefixExpr
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
| NEW simpleType ('[' expression ']')+ ('[' ']')+ ('[' expression ']')+ # fakeNewExpr
| NEW simpleType (squareBracketWithExpression)+ # newExpr
| NEW simpleType ('('')')? # newExpr
// | NEW simpleType '(' (expression (',' expression)* )?  ')' # newExpr
| ID # idExpr
| constant # constExpr
| expression'.'ID # memberExpr
| expression'['expression']' # arrayExpr;


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