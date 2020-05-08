package ir;

public class OpStmt extends YyStmt {
    public enum Op {
        PLUS, MINUS, MUL, DIV, MOD,
        AND, OR, XOR,
        LSHIFT, RSHIFT, ARSHIFT,
        EQ, NEQ, LTH, LEQ, GTH, GEQ
    }
    private Op op;
    private Item opr1, opr2, result;

    public OpStmt(Op op, Item opr1, Item opr2, Item result) {
        this.op = op;
        this.opr1 = opr1;
        this.opr2 = opr2;
        this.result = result;
    }

    @Override
    public String toString() {
        switch (op) {
            case PLUS: return result + " = " + opr1 + " + " + opr2;
            case MINUS: return result + " = " + opr1 + " - " + opr2;
            case MUL: return result + " = " + opr1 + " * " + opr2;
            case DIV: return result + " = " + opr1 + " / " + opr2;
            case MOD: return result + " = " + opr1 + " % " + opr2;
            case AND: return result + " = " + opr1 + " & " + opr2;
            case OR: return result + " = " + opr1 + " | " + opr2;
            case XOR: return result + " = " + opr1 + " ^ " + opr2;
            case LSHIFT: return result + " = " + opr1 + " << " + opr2;
            case RSHIFT: return result + " = " + opr1 + " >> " + opr2;
            case ARSHIFT: return result + " = " + opr1 + " >>> " + opr2;
            case EQ: return result + " = " + opr1 + " == " + opr2;
            case NEQ: return result + " = " + opr1 + " != " + opr2;
            case LTH: return result + " = " + opr1 + " < " + opr2;
            case LEQ: return result + " = " + opr1 + " <= " + opr2;
            case GTH: return result + " = " + opr1 + " > " + opr2;
            case GEQ: return result + " = " + opr1 + " >= " + opr2;
            default: return "";
        }
    }
}
