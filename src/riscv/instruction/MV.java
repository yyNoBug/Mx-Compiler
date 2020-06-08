package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class MV extends Instruction {
    REGISTER dest;
    REGISTER src;

    public MV(REGISTER dest, REGISTER src) {
        this.dest = dest;
        this.src = src;
    }

    public REGISTER getDest() {
        return dest;
    }

    public REGISTER getSrc() {
        return src;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        src = super.resolveSrc(virtualMap, itr, src, 3);
        dest = super.resolveDest(virtualMap, itr, dest, function,5);
    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>() {{ add(dest); }};
    }

    @Override
    public HashSet<REGISTER> getUses() {
        return new HashSet<>() {{ add(src); }};
    }

    @Override
    public void replaceUse(REGISTER old, REGISTER newReg) {
        if (src == old) src = newReg;
    }

    @Override
    public void replaceRd(REGISTER old, REGISTER newReg) {
        if (dest == old) dest = newReg;
    }

    @Override
    public String toString() {
        return "\tmv\t" + dest + "," + src;
    }
}
