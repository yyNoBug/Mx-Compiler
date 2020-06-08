package riscv.instruction;

import riscv.addr.Address;
import riscv.addr.StackAddr;
import riscv.register.REGISTER;

import java.util.HashSet;
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
    public HashSet<REGISTER> getDefs() {
        validate();
        return super.getDefs();
    }

    @Override
    public void replaceRd(REGISTER old, REGISTER newReg) {
        validate();
        super.replaceRd(old, newReg);
    }

    @Override
    public String toString() {
        validate();
        return super.toString();
    }
}
