package ast;

public class PrefixExprNode extends ExprNode {
    public enum Op {
        PREFIX_INC, PREFIX_DEC, POS, NEG, LOGIC_NOT, BITWISE_NOT
    };

    private Op op;
    private ExprNode expr;

    public PrefixExprNode(Location loc, Op op, ExprNode expr) {
        super(loc);
        this.op = op;
        this.expr = expr;
    }

    public Op getOp() {
        return op;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
