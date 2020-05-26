package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class DirSTRING extends Instruction {
    private String str;

    public DirSTRING(String str) {
        this.str = str;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }

    @Override
    public String toString() {
        return "\t.string\t" + str;
    }
}
