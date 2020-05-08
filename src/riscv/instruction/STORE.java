package riscv.instruction;

import riscv.Address;
import riscv.register.REGISTER;

public class STORE extends Instruction {
    REGISTER reg;
    Address addr;

    @Override
    public String toString() {
        return "\tsw\t" + reg + ", " + addr;
    }
}
