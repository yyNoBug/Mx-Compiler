package frontend;

import ast.*;
import scope.TopLevelScope;

public class ClassDeclScanner implements ASTVisitor {
    private TopLevelScope globalScope;

    public ClassDeclScanner(TopLevelScope globalScope) {
        this.globalScope = globalScope;
    }

    @Override
    public void visit(ProgramNode node) {
        node.getSection_list().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(VarDeclSingleNode node) {

    }

    @Override
    public void visit(FunDeclNode node) {

    }

    @Override
    public void visit(ClassDeclNode node) {
        globalScope.defineClass(node.getId());
    }

    @Override
    public void visit(ClassConstructorNode node) {

    }

    @Override
    public void visit(StatementBlockNode node) {

    }

    @Override
    public void visit(ExprStatementNode node) {

    }

    @Override
    public void visit(VarDeclStatementNode node) {
        node.getVariable().accept(this);
    }

    @Override
    public void visit(IfStatementNode node) {

    }

    @Override
    public void visit(WhileStatementNode node) {

    }

    @Override
    public void visit(ForStatementNode node) {

    }

    @Override
    public void visit(ContinueStatementNode node) {

    }

    @Override
    public void visit(BreakStatementNode node) {

    }

    @Override
    public void visit(ReturnStatementNode node) {

    }

    @Override
    public void visit(SuffixExprNode node) {

    }

    @Override
    public void visit(FunCallExprNode node) {

    }

    @Override
    public void visit(ArrayExprNode node) {

    }

    @Override
    public void visit(MemberExprNode node) {

    }

    @Override
    public void visit(PrefixExprNode node) {

    }

    @Override
    public void visit(NewExprNode node) {

    }

    @Override
    public void visit(BinaryExprNode node) {

    }

    @Override
    public void visit(AssignExprNode node) {

    }

    @Override
    public void visit(IdExprNode node) {

    }

    @Override
    public void visit(ThisExprNode node) {

    }

    @Override
    public void visit(NumConstNode node) {

    }

    @Override
    public void visit(StringConstNode node) {

    }

    @Override
    public void visit(BoolConstNode node) {

    }

    @Override
    public void visit(NullConstNode node) {

    }

    @Override
    public void visit(TypeNode node) {

    }

    @Override
    public void visit(ArrayTypeNode node) {

    }
}
