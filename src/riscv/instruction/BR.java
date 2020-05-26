package riscv.instruction;

import riscv.RVBlock;
import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class BR extends Instruction {
    private OpClass.Cmp op;
    private REGISTER lhs, rhs;
    private RVBlock dest;

    public BR(OpClass.Cmp op, REGISTER lhs, REGISTER rhs, RVBlock dest) {
        this.op = op;
        this.lhs = lhs;
        this.rhs = rhs;
        this.dest = dest;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        lhs = super.resolveSrc(virtualMap, itr, lhs, 3);
        rhs = super.resolveSrc(virtualMap, itr, rhs, 4);
    }

    @Override
    public String toString() {
        return "\t" + op + '\t' + lhs + rhs + dest;
    }
}
