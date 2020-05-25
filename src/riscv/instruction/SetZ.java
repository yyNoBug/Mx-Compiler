package riscv.instruction;

import riscv.register.REGISTER;

public class SetZ extends Instruction {
    private OpClass.Cmp op;
    private REGISTER dest;
    private REGISTER cond;

    public SetZ(OpClass.Cmp op, REGISTER dest, REGISTER cond) {
        this.op = op;
        this.dest = dest;
        this.cond = cond;
    }

    @Override
    public String toString() {
        switch (op) {
            case EQ:
                return "\tseqz" + dest + cond;
            case NEQ:
                return "\tsnez" + dest + cond;
            case GT:
                return "\tsgtz" + dest + cond;
            case LT:
                return "\tsltz" + dest + cond;
            default:
                assert false;
                return "";
        }
    }
}
