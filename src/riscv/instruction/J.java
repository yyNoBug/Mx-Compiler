package riscv.instruction;

import riscv.RVBlock;
import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class J extends Instruction {
    private RVBlock dest;

    public J(RVBlock dest) {
        this.dest = dest;
    }

    public RVBlock getDest() {
        return dest;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }

    @Override
    public String toString() {
        return "\tj\t" + dest;
    }
}
