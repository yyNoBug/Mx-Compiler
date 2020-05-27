package riscv.instruction;

import riscv.addr.Address;
import riscv.addr.StackAddr;
import riscv.register.REGISTER;

import java.util.ListIterator;

public class LAddr extends LI {
    private Address addr;

    public LAddr(REGISTER reg, Address addr) {
        super(reg, 0);
        assert addr instanceof StackAddr;
        this.addr = addr;
    }

    private void validate() {
        super.imm = addr.getOffset();
    }

    @Override
    public void addrValidate(ListIterator<Instruction> itr) {
        addr = super.changeAddr(addr, itr);
    }

    @Override
    public String toString() {
        validate();
        return super.toString();
    }
}
