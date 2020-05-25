package riscv.instruction;

import riscv.RVBlock;
import riscv.register.REGISTER;

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
    public String toString() {
        return "\t" + op + '\t' + lhs + rhs + dest;
    }
}
