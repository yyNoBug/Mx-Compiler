package riscv.instruction;

import riscv.register.REGISTER;

public class LI extends Instruction {
    REGISTER reg;
    int imm;

    @Override
    public String toString() {
        return "\tli" + reg + ", " + "imm";
    }
}
