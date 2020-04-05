// Generated from C:/Users/yy/IdeaProjects/Mx-Compiler/ANTLR\MxLang.g4 by ANTLR 4.8
package parser;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link MxLangParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface MxLangVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link MxLangParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(MxLangParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#section}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSection(MxLangParser.SectionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#classDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassDefinition(MxLangParser.ClassDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#functionDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDefinition(MxLangParser.FunctionDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#variableDefinition}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDefinition(MxLangParser.VariableDefinitionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#type}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitType(MxLangParser.TypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#simpleType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSimpleType(MxLangParser.SimpleTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#arrayType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayType(MxLangParser.ArrayTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#constant}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstant(MxLangParser.ConstantContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#functionParameterList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionParameterList(MxLangParser.FunctionParameterListContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#parameterDeclaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameterDeclaration(MxLangParser.ParameterDeclarationContext ctx);
	/**
	 * Visit a parse tree produced by the {@code newExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNewExpr(MxLangParser.NewExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParExpr(MxLangParser.ParExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code prefixExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrefixExpr(MxLangParser.PrefixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code thisExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThisExpr(MxLangParser.ThisExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code funCallExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunCallExpr(MxLangParser.FunCallExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code arrayExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArrayExpr(MxLangParser.ArrayExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code suffixExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSuffixExpr(MxLangParser.SuffixExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code memberExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMemberExpr(MxLangParser.MemberExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code binaryExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBinaryExpr(MxLangParser.BinaryExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignExpr(MxLangParser.AssignExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code idExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdExpr(MxLangParser.IdExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code constExpr}
	 * labeled alternative in {@link MxLangParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitConstExpr(MxLangParser.ConstExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#variableDeclarator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableDeclarator(MxLangParser.VariableDeclaratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#vardeclStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVardeclStatement(MxLangParser.VardeclStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(MxLangParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#loopStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLoopStatement(MxLangParser.LoopStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#jmpStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJmpStatement(MxLangParser.JmpStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#exprStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExprStatement(MxLangParser.ExprStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#breakStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBreakStatement(MxLangParser.BreakStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#continueStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitContinueStatement(MxLangParser.ContinueStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(MxLangParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#statementBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatementBlock(MxLangParser.StatementBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#squareBracket}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSquareBracket(MxLangParser.SquareBracketContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#squareBracketWithExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSquareBracketWithExpression(MxLangParser.SquareBracketWithExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(MxLangParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(MxLangParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#classConstructor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitClassConstructor(MxLangParser.ClassConstructorContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#blankStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlankStatement(MxLangParser.BlankStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link MxLangParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(MxLangParser.StatementContext ctx);
}