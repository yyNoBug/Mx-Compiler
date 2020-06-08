package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class SPGrow extends Instruction {
    private RVFunction function;

    public SPGrow(RVFunction function) {
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
        return "\taddi\t" + REGISTER.sp + "," + REGISTER.sp + "," + -function.getSize();
    }
}
