package ast;

import scope.DefinedFunction;

import java.util.List;

public class FunCallExprNode extends ExprNode {
    private ExprNode funcExpr;
    private List<ExprNode> parameterList;
    private DefinedFunction entity;

    public FunCallExprNode(Location loc, ExprNode function, List<ExprNode> parameterList) {
        super(loc);
        this.funcExpr = function;
        this.parameterList = parameterList;
    }

    public ExprNode getFuncExpr() {
        return funcExpr;
    }

    public List<ExprNode> getParameterList() {
        return parameterList;
    }

    public DefinedFunction getEntity() {
        return entity;
    }

    public void setEntity(DefinedFunction entity) {
        this.entity = entity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
