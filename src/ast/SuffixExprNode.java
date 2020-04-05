package ast;

public class SuffixExprNode extends  ExprNode {
    public enum Op {
        SUFFIX_INC, SUFFIX_DEC
    };

    private Op op;
    private ExprNode expr;

    public SuffixExprNode(Location loc, SuffixExprNode.Op op, ExprNode expr) {
        super(loc);
        this.op = op;
        this.expr = expr;
    }

    public SuffixExprNode.Op getOp() {
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
