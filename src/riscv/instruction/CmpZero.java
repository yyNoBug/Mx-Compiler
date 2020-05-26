package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

class CmpZero extends Instruction {
    private OpClass.Cmp op;
    private REGISTER reg, dest;

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }
}
