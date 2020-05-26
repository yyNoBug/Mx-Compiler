package riscv.instruction;

import riscv.RVFunction;
import riscv.RVGlobal;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class LG extends Instruction {
    REGISTER reg;
    RVGlobal global;

    public LG(REGISTER reg, RVGlobal global) {
        this.reg = reg;
        this.global = global;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {
        reg = super.resolveDest(virtualMap, itr, reg, function, 3);
    }

    @Override
    public String toString() {
        return "\tlw\t" + reg + "," + global;
    }
}
