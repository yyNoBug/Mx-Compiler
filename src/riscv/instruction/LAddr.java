package riscv.instruction;

import riscv.addr.StackAddr;
import riscv.register.REGISTER;

public class LAddr extends LI {
    private StackAddr addr;

    public LAddr(REGISTER reg, StackAddr addr) {
        super(reg, 0);
        this.addr = addr;
    }

    private void validate() {
        super.imm = addr.calculate();
    }

    @Override
    public String toString() {
        validate();
        return super.toString();
    }
}
