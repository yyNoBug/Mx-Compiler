package riscv.instruction;

import riscv.RVFunction;
import riscv.RVGlobal;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

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

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {
        rt = super.resolveDest(virtualMap, itr, rt, function, 5);
        rd = super.resolveSrc(virtualMap, itr, rd, 3);
    }

    @Override
    public String toString() {
        return "\tsw\t" + rd + "," + global + "," + rt;
    }
}
