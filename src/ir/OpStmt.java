package ir;

public class OpStmt extends YyStmt {
    public enum Op {
        PLUS, MINUS, MUL, DIV, MOD,
        AND, OR, XOR,
        LSHIFT, RSHIFT, ARSHIFT,
        EQ, NEQ, LTH, LEQ, GTH, GEQ
    }
    private Op op;
    private Register opr1, opr2, result;

    public OpStmt(Op op, Register opr1, Register opr2, Register result) {
        this.op = op;
        this.opr1 = opr1;
        this.opr2 = opr2;
        this.result = result;
    }
}
