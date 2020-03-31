package ast;

public class ReturnStatementBlock extends JmpStatementNode {
    private ExprNode expr;

    public ReturnStatementBlock(Location loc, ExprNode expr) {
        super(loc);
        this.expr = expr;
    }

    public ExprNode getExpr() {
        return expr;
    }
}
