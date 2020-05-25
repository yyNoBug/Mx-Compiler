package riscv.instruction;

import riscv.register.REGISTER;

public class MV extends Instruction {
    REGISTER dest;
    REGISTER src;

    public MV(REGISTER dest, REGISTER src) {
        this.dest = dest;
        this.src = src;
    }

    @Override
    public String toString() {
        return "\tmv\t" + dest + ", " + src;
    }
}
