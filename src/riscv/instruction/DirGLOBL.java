package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class DirGLOBL extends Instruction {
    String s;

    public DirGLOBL(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return "\t.globl\t" + s;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }
}
