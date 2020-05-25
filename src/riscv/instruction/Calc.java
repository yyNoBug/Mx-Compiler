package riscv.instruction;

import riscv.register.REGISTER;

public class Calc extends Instruction {
    private OpClass.Op op;
    private REGISTER dest, lhs, rhs;

    public Calc(OpClass.Op op, REGISTER dest, REGISTER lhs, REGISTER rhs) {
        this.op = op;
        this.dest = dest;
        this.lhs = lhs;
        this.rhs = rhs;
    }
}
