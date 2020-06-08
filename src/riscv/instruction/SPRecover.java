package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class SPRecover extends Instruction {
    private RVFunction function;

    public SPRecover(RVFunction function) {
        this.function = function;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>(){{add(REGISTER.sp);}};
    }

    @Override
    public HashSet<REGISTER> getUses() {
        return new HashSet<>(){{add(REGISTER.sp);}};
    }

    @Override
    public String toString() {
        if (function.getSize() < 2048) return "\taddi\t" + REGISTER.sp + "," + REGISTER.sp + "," + function.getSize();
        else return "\tli\t" + REGISTER.temps[0] + "," + function.getSize() + "\n"
                + "\tadd\t" + REGISTER.sp + "," + REGISTER.sp + "," + REGISTER.temps[0];
    }
}
