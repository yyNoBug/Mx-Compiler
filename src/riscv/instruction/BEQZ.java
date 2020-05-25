package riscv.instruction;

import riscv.RVBlock;
import riscv.register.REGISTER;

public class BEQZ extends Instruction {
    private REGISTER cond;
    private RVBlock dest;

    public BEQZ(REGISTER cond, RVBlock dest) {
        this.cond = cond;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "\tbeqz" + cond + dest;
    }
}
