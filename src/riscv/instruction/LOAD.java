package riscv.instruction;

import riscv.RVFunction;
import riscv.addr.Address;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class LOAD extends Instruction {
    REGISTER reg;
    Address addr;

    public LOAD(REGISTER reg, Address addr) {
        this.reg = reg;
        this.addr = addr;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        super.resolveAddr(virtualMap, itr, addr, 4);
        reg = super.resolveDest(virtualMap, itr, reg, function, 5);
    }

    @Override
    public void addrValidate(ListIterator<Instruction> itr) {
        addr = super.changeAddr(addr, itr);
    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>() {{ add(reg); }};
    }

    @Override
    public HashSet<REGISTER> getUses() {
        return new HashSet<>() {{ add(addr.getBase()); }};
    }

    @Override
    public void replaceUse(REGISTER old, REGISTER newReg) {
        if (addr.getBase() == old) addr.setBase(newReg);
    }

    @Override
    public void replaceRd(REGISTER old, REGISTER newReg) {
        if (reg == old) reg = newReg;
    }

    @Override
    public String toString() {
        return "\tlw\t" + reg + "," + addr;
    }
}
