package frontend;

import ast.*;
import scope.*;

import java.util.Stack;

public class ClassMemberScanner implements ASTVisitor {
    private TopLevelScope globalScope;
    private Stack<Scope> scopeStack = new Stack<>();
    private DefinedClass curClass = null;

    public ClassMemberScanner(TopLevelScope globalScope) {
        this.globalScope = globalScope;
    }

    private Scope currentScope() {
        return scopeStack.peek();
    }

    private LocalScope popScope() {
        return (LocalScope) scopeStack.pop();
    }

    @Override
    public void visit(ProgramNode node) {
        scopeStack.push(globalScope);
        node.getSection_list().forEach(x -> {
            if (x instanceof ClassDeclNode) x.accept(this);
        });
    }

    @Override
    public void visit(VarDeclSingleNode node) {
        DefinedVariable var = new DefinedVariable(node);
        currentScope().defineVariable(var);
        curClass.defineMemberVariable(var);
    }

    @Override
    public void visit(FunDeclNode node) {
        DefinedFunction function = new DefinedFunction(node, currentScope());
        currentScope().defineFunction(function);
        curClass.defineMemberFunction(function);
    }

    @Override
    public void visit(ClassDeclNode node) {
        DefinedClass classEntity = globalScope.getClass(node.getId());
        curClass = classEntity;
        scopeStack.push(classEntity.getInnerScope());
        if (node.getConstructor() != null) node.getConstructor().accept(this);
        node.getMemberVars().forEach(x -> x.accept(this));
        node.getMemberFuns().forEach(x -> x.accept(this));
        popScope();
        curClass = null;
    }

    @Override
    public void visit(ClassConstructorNode node) {
        if (!curClass.getName().equals(node.getName()))
            throw new SemanticException("Constructor must have same name with its class.");
        DefinedFunction constructor = new DefinedFunction(node, currentScope());
        currentScope().defineFunction(constructor);
        curClass.defineConstructor(constructor);
    }

    @Override
    public void visit(StatementBlockNode node) {

    }

    @Override
    public void visit(ExprStatementNode node) {

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
