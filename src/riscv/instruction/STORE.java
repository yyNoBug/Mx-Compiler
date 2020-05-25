package riscv.instruction;

import riscv.addr.Address;
import riscv.register.REGISTER;

public class STORE extends Instruction {
    REGISTER reg;
    Address addr;

    public STORE(REGISTER reg, Address addr) {
        this.reg = reg;
        this.addr = addr;
    }

    @Override
    public String toString() {
        return "\tsw\t" + reg + ", " + addr;
    }
}
