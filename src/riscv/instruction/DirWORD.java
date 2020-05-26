package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class DirWORD extends Instruction {
    private int num;

    public DirWORD(int num) {
        this.num = num;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }

    @Override
    public String toString() {
        return "\t.word\t" + num;
    }
}
