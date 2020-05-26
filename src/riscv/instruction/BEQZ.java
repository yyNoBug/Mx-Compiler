package riscv.instruction;

import riscv.RVBlock;
import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class BEQZ extends Instruction {
    private REGISTER cond;
    private RVBlock dest;

    public BEQZ(REGISTER cond, RVBlock dest) {
        this.cond = cond;
        this.dest = dest;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        cond = super.resolveSrc(virtualMap, itr, cond, 3);
    }

    @Override
    public String toString() {
        return "\tbeqz\t" + cond + "," + dest;
    }
}
