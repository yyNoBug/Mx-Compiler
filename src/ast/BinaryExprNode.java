package ast;

public class BinaryExprNode extends ExprNode {

    public enum Op {
        MUL, DIV, MOD,
        ADD, SUB, SLA, SRA,
        GTH, LTH, GEQ, LEQ,
        EQ, NEQ,
        BITWISE_AND,
        BITWISE_XOR,
        BITWISE_OR,
        LOGIC_AND,
        LOGIC_OR
    }

    private Op op;
    private ExprNode lhs, rhs;

    public BinaryExprNode(Location loc, Op op, ExprNode lhs, ExprNode rhs) {
        super(loc);
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Op getOp() {
        return op;
    }

    public ExprNode getLhs() {
        return lhs;
    }

    public ExprNode getRhs() {
        return rhs;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
