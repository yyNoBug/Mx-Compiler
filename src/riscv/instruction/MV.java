package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class MV extends Instruction {
    REGISTER dest;
    REGISTER src;

    public MV(REGISTER dest, REGISTER src) {
        this.dest = dest;
        this.src = src;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        src = super.resolveSrc(virtualMap, itr, src, 3);
        dest = super.resolveDest(virtualMap, itr, dest, function,5);
    }

    @Override
    public String toString() {
        return "\tmv\t" + dest + "," + src;
    }
}
