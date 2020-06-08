package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class LI extends Instruction {
    REGISTER reg;
    int imm;

    public LI(REGISTER reg, int imm) {
        this.reg = reg;
        this.imm = imm;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        reg = super.resolveDest(virtualMap, itr, reg, function, 3);
    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>() {{ add(reg); }};
    }

    @Override
    public void replaceRd(REGISTER old, REGISTER newReg) {
        if (reg == old) reg = newReg;
    }

    @Override
    public String toString() {
        return "\tli\t" + reg + "," + imm;
    }
}
