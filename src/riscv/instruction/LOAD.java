package riscv.instruction;

import riscv.Address;
import riscv.register.REGISTER;

public class LOAD extends Instruction {
    REGISTER reg;
    Address addr;

    @Override
    public String toString() {
        return "\tlw\t" + reg + ", " + addr;
    }
}
