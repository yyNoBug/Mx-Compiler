package ast;

import scope.Entity;

public class MemberExprNode extends ExprNode {
    private ExprNode expr;
    private String member;
    private Entity entity;

    public MemberExprNode(Location loc, ExprNode expr, String member) {
        super(loc);
        this.expr = expr;
        this.member = member;
    }

    public ExprNode getExpr() {
        return expr;
    }

    public String getMember() {
        return member;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    public Entity getEntity() {
        return entity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
