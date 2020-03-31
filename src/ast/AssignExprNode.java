package ast;

public class AssignExprNode extends ExprNode {
    private ExprNode lhs, rhs;

    public AssignExprNode(Location loc, ExprNode lhs, ExprNode rhs) {
        super(loc);
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public ExprNode getLhs() {
        return lhs;
    }

    public ExprNode getRhs() {
        return rhs;
    }
}
