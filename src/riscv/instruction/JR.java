package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
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
    public HashSet<REGISTER> getUses() {
        return new HashSet<>(){{add(register);}};
    }

    @Override
    public void replaceUse(REGISTER old, REGISTER newReg) {
        if (register == old) register = newReg;
    }

    @Override
    public String toString() {
        return "\tjr\t" + register;
    }
}
