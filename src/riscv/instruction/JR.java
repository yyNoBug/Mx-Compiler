package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class JR extends Instruction {
    private REGISTER register;

    public JR(REGISTER register) {
        this.register = register;
    }
    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        register = super.resolveSrc(virtualMap, itr, register, 3);
    }
    @Override
    public String toString() {
        return "\tjr\t" + register;
    }
}
