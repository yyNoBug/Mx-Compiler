package riscv.instruction;

import riscv.RVFunction;
import riscv.RVString;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class LA extends Instruction {
    REGISTER reg;
    RVString str;

    public LA(REGISTER reg, RVString str) {
        this.reg = reg;
        this.str = str;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {
        reg = super.resolveDest(virtualMap, itr, reg, function, 5);
    }

    @Override
    public String toString() {
        return "\tla\t" + reg + "," + str;
    }
}
