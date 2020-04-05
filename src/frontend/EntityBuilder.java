package frontend;

import ast.*;
import scope.*;

import java.util.Stack;

public class EntityBuilder implements ASTVisitor {

    private TopLevelScope globalScope;
    private Stack<Scope> scopeStack = new Stack<>();
    private DefinedClass curClass = null;
    private int loopCounter = 0;

    public EntityBuilder(TopLevelScope globalScope) {
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
        node.getSection_list().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(VarDeclSingleNode node) {
        node.getType().accept(this);
        if (node.getExpr() != null) node.getExpr().accept(this);
        DefinedVariable var = new DefinedVariable(node);
        currentScope().defineVariable(var);
    }

    @Override
    public void visit(FunDeclNode node) {
        node.getType().accept(this);
        DefinedFunction function = (DefinedFunction) currentScope().get(node.getName());
        if (function.getInnerScope() == null) throw new SemanticException(node.getLocation(), "error!!");
        scopeStack.push(function.getInnerScope());
        visit(node.getBody());
        popScope();
    }

    @Override
    public void visit(ClassDeclNode node) {
        DefinedClass entity = globalScope.getClass(node.getId());
        curClass = entity;
        if (entity.getInnerScope() == null) throw new SemanticException(node.getLocation(), "error!!");
        scopeStack.push(entity.getInnerScope());
        if (node.getConstructor() != null) node.getConstructor().accept(this);
        node.getMemberFuns().forEach(x -> x.accept(this));
        popScope();
        curClass = null;
    }

    @Override
    public void visit(ClassConstructorNode node) {
        DefinedFunction function = (DefinedFunction) currentScope().get(node.getName());
        if (function.getInnerScope() == null) throw new SemanticException(node.getLocation(), "error!!");
        scopeStack.push(function.getInnerScope());
        visit(node.getBody());
        popScope();
    }

    @Override
    public void visit(StatementBlockNode node) {
        LocalScope localScope = new LocalScope(currentScope());
        scopeStack.push(localScope);
        node.getStmList().forEach(x -> x.accept(this));
        popScope();
    }

    @Override
    public void visit(ExprStatementNode node) {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(IfStatementNode node) {
        node.getCondition().accept(this);
        if (node.getThenStatement() != null) node.getThenStatement().accept(this);
        if (node.getElseStatement() != null) node.getElseStatement().accept(this);
    }

    @Override
    public void visit(WhileStatementNode node) {
        loopCounter++;
        node.getCondition().accept(this);
        if (node.getBody() != null)
            node.getBody().accept(this);
        loopCounter--;
    }

    @Override
    public void visit(ForStatementNode node) {
        LocalScope localScope = new LocalScope(currentScope());
        scopeStack.push(localScope);
        loopCounter++;
        if (node.getInit() != null) node.getInit().accept(this);
        if (node.getCond() != null) node.getCond().accept(this);
        if (node.getStep() != null) node.getStep().accept(this);
        if (node.getStatement() != null) node.getStatement().accept(this);
        loopCounter--;
        popScope();
    }

    @Override
    public void visit(ContinueStatementNode node) {
        if (loopCounter == 0) throw new SemanticException("Continue statement not in loop.");
    }

    @Override
    public void visit(BreakStatementNode node) {
        if (loopCounter == 0) throw new SemanticException("Break statement not in loop.");
    }

    @Override
    public void visit(ReturnStatementNode node) {
        if (node.getExpr() != null)
            node.getExpr().accept(this);
    }

    @Override
    public void visit(SuffixExprNode node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(FunCallExprNode node) {
        node.getFuncExpr().accept(this);
        node.getParameterList().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(ArrayExprNode node) {
        node.getArray().accept(this);
        node.getIndex().accept(this);
    }

    @Override
    public void visit(MemberExprNode node) {
        node.getExpr().accept(this);
//        DefinedClass classEntity = node.getExpr().getType().getEntity();
//        if (classEntity == null)
//            throw new SemanticException(node.getLocation(), "Expression called not resolved.");
//        Entity memberEntity = classEntity.resolveMember(node.getMember());
//        node.setEntity(memberEntity);
    }

    @Override
    public void visit(PrefixExprNode node) {
        node.getExpr().accept(this);
    }

    @Override
    public void visit(NewExprNode node) {
        node.getNewType().accept(this);
        node.getDims().forEach(x -> x.accept(this));
    }

    @Override
    public void visit(BinaryExprNode node) {
        node.getLhs().accept(this);
        node.getRhs().accept(this);
    }

    @Override
    public void visit(AssignExprNode node) {
        node.getLhs().accept(this);
        node.getRhs().accept(this);
    }

    @Override
    public void visit(IdExprNode node) {
        // Problem: ID can be class name.
        Entity entity = currentScope().get(node.getId());
        node.setEntity(entity);
    }

    @Override
    public void visit(ThisExprNode node) {
        if (curClass == null)
            throw new SemanticException("This statement not in class.");
        node.setClassEntity(curClass);
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
        node.getType().resolve(globalScope);
    }

    @Override
    public void visit(ArrayTypeNode node) {
        node.getType().resolve(globalScope);
    }
}
