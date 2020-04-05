// Generated from C:/Users/yy/IdeaProjects/Mx-Compiler/ANTLR\MxLang.g4 by ANTLR 4.8
package parser;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link MxLangParser}.
 */
public interface MxLangListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link MxLangParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(MxLangParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(MxLangParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#section}.
	 * @param ctx the parse tree
	 */
	void enterSection(MxLangParser.SectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#section}.
	 * @param ctx the parse tree
	 */
	void exitSection(MxLangParser.SectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(MxLangParser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(MxLangParser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(MxLangParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(MxLangParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void enterVariableDefinition(MxLangParser.VariableDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#variableDefinition}.
	 * @param ctx the parse tree
	 */
	void exitVariableDefinition(MxLangParser.VariableDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(MxLangParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(MxLangParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void enterSimpleType(MxLangParser.SimpleTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#simpleType}.
	 * @param ctx the parse tree
	 */
	void exitSimpleType(MxLangParser.SimpleTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void enterArrayType(MxLangParser.ArrayTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#arrayType}.
	 * @param ctx the parse tree
	 */
	void exitArrayType(MxLangParser.ArrayTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#constant}.
	 * @param ctx the parse tree
	 */
	void enterConstant(MxLangParser.ConstantContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#constant}.
	 * @param ctx the parse tree
	 */
	void exitConstant(MxLangParser.ConstantContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#functionParameterList}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParameterList(MxLangParser.FunctionParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#functionParameterList}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParameterList(MxLangParser.FunctionParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterParameterDeclaration(MxLangParser.ParameterDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitParameterDeclaration(MxLangParser.ParameterDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNewExpr(MxLangParser.NewExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNewExpr(MxLangParser.NewExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParExpr(MxLangParser.ParExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParExpr(MxLangParser.ParExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixExpr(MxLangParser.PrefixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixExpr(MxLangParser.PrefixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterThisExpr(MxLangParser.ThisExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitThisExpr(MxLangParser.ThisExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code funCallExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunCallExpr(MxLangParser.FunCallExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code funCallExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunCallExpr(MxLangParser.FunCallExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayExpr(MxLangParser.ArrayExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayExpr(MxLangParser.ArrayExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterSuffixExpr(MxLangParser.SuffixExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitSuffixExpr(MxLangParser.SuffixExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberExpr(MxLangParser.MemberExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberExpr(MxLangParser.MemberExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpr(MxLangParser.BinaryExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpr(MxLangParser.BinaryExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignExpr(MxLangParser.AssignExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignExpr(MxLangParser.AssignExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpr(MxLangParser.IdExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpr(MxLangParser.IdExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConstExpr(MxLangParser.ConstExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConstExpr(MxLangParser.ConstExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarator(MxLangParser.VariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#variableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarator(MxLangParser.VariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#vardeclStatement}.
	 * @param ctx the parse tree
	 */
	void enterVardeclStatement(MxLangParser.VardeclStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#vardeclStatement}.
	 * @param ctx the parse tree
	 */
	void exitVardeclStatement(MxLangParser.VardeclStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(MxLangParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(MxLangParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void enterLoopStatement(MxLangParser.LoopStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#loopStatement}.
	 * @param ctx the parse tree
	 */
	void exitLoopStatement(MxLangParser.LoopStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#jmpStatement}.
	 * @param ctx the parse tree
	 */
	void enterJmpStatement(MxLangParser.JmpStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#jmpStatement}.
	 * @param ctx the parse tree
	 */
	void exitJmpStatement(MxLangParser.JmpStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void enterExprStatement(MxLangParser.ExprStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#exprStatement}.
	 * @param ctx the parse tree
	 */
	void exitExprStatement(MxLangParser.ExprStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(MxLangParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#breakStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(MxLangParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(MxLangParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#continueStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(MxLangParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(MxLangParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(MxLangParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void enterStatementBlock(MxLangParser.StatementBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#statementBlock}.
	 * @param ctx the parse tree
	 */
	void exitStatementBlock(MxLangParser.StatementBlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#squareBracket}.
	 * @param ctx the parse tree
	 */
	void enterSquareBracket(MxLangParser.SquareBracketContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#squareBracket}.
	 * @param ctx the parse tree
	 */
	void exitSquareBracket(MxLangParser.SquareBracketContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#squareBracketWithExpression}.
	 * @param ctx the parse tree
	 */
	void enterSquareBracketWithExpression(MxLangParser.SquareBracketWithExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#squareBracketWithExpression}.
	 * @param ctx the parse tree
	 */
	void exitSquareBracketWithExpression(MxLangParser.SquareBracketWithExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(MxLangParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(MxLangParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(MxLangParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(MxLangParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#classConstructor}.
	 * @param ctx the parse tree
	 */
	void enterClassConstructor(MxLangParser.ClassConstructorContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#classConstructor}.
	 * @param ctx the parse tree
	 */
	void exitClassConstructor(MxLangParser.ClassConstructorContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#blankStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlankStatement(MxLangParser.BlankStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#blankStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlankStatement(MxLangParser.BlankStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link MxLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(MxLangParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link MxLangParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(MxLangParser.StatementContext ctx);
}