package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class DirSECTION extends Instruction{
    private String s;

    public DirSECTION(String s) {
        this.s = s;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }

    @Override
    public String toString() {
        return "\t.section\t" + s;
    }
}
