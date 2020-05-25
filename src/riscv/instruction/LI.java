package riscv.instruction;

import riscv.register.REGISTER;

public class LI extends Instruction {
    REGISTER reg;
    int imm;

    public LI(REGISTER reg, int imm) {
        this.reg = reg;
        this.imm = imm;
    }

    @Override
    public String toString() {
        return "\tli\t" + reg + "," + "imm";
    }
}
