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

    public void setOp(Op op) {
        this.op = op;
    }

    public void setLhs(ExprNode lhs) {
        this.lhs = lhs;
    }

    public void setRhs(ExprNode rhs) {
        this.rhs = rhs;
    }
}
