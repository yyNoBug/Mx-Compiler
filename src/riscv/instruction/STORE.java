package riscv.instruction;

import riscv.RVFunction;
import riscv.addr.Address;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class STORE extends Instruction {
    REGISTER reg;
    Address addr;

    public STORE(REGISTER reg, Address addr) {
        this.reg = reg;
        this.addr = addr;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        super.resolveAddr(virtualMap, itr, addr, 4);
        reg = super.resolveSrc(virtualMap, itr, reg, 3);
    }

    @Override
    public void addrValidate(ListIterator<Instruction> itr) {
        addr = super.changeAddr(addr, itr);
    }

    @Override
    public String toString() {
        return "\tsw\t" + reg + "," + addr;
    }
}
