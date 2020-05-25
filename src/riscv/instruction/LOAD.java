package riscv.instruction;

import riscv.addr.Address;
import riscv.register.REGISTER;

public class LOAD extends Instruction {
    REGISTER reg;
    Address addr;

    public LOAD(REGISTER reg, Address addr) {
        this.reg = reg;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "\tlw\t" + reg + ", " + addr;
    }
}
