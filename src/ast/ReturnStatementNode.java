package ast;

public class ReturnStatementNode extends StatementNode {
    private ExprNode expr;

    public ReturnStatementNode(Location loc, ExprNode expr) {
        super(loc);
        this.expr = expr;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
