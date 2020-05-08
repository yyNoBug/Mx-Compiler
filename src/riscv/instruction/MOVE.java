package riscv.instruction;

import riscv.register.REGISTER;

public class MOVE extends Instruction {
    REGISTER dest;
    REGISTER src;

    @Override
    public String toString() {
        return "\tmv\t" + dest + ", " + src;
    }
}
