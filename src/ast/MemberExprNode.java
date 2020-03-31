package ast;

public class MemberExprNode extends ExprNode {
    private ExprNode expr;
    private String member;

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
}
