package riscv.instruction;

import riscv.RVFunction;
import riscv.RVGlobal;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class SG extends Instruction {
    REGISTER rd;
    RVGlobal global;
    REGISTER rt;

    public SG(REGISTER rd, RVGlobal global, REGISTER rt) {
        this.rd = rd;
        this.global = global;
        this.rt = rt;
    }

    public REGISTER getRd() {
        return rd;
    }

    public REGISTER getRt() {
        return rt;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {
        rd = super.resolveSrc(virtualMap, itr, rd, 3);
        rt = super.resolveDest(virtualMap, itr, rt, function, 5);
    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>(){{add(rt);}};
    }

    @Override
    public HashSet<REGISTER> getUses() {
        return new HashSet<>(){{add(rd);}};
    }

    @Override
    public void replaceUse(REGISTER old, REGISTER newReg) {
        if (rd == old) rd = newReg;
    }

    @Override
    public void replaceRd(REGISTER old, REGISTER newReg) {
        if (rt == old) rt = newReg;
    }

    @Override
    public String toString() {
        return "\tsw\t" + rd + "," + global + "," + rt;
    }
}
