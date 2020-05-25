package riscv.instruction;

import riscv.register.REGISTER;

public class JR extends Instruction {
    private REGISTER register;

    public JR(REGISTER register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "\tjr\t" + register;
    }
}
