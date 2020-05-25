package riscv.instruction;

import riscv.register.REGISTER;

public class CalcI extends Instruction {
    private OpClass.Op op;
    private REGISTER lhs, dest;
    private int imm;

    public CalcI(OpClass.Op op, REGISTER dest, REGISTER lhs, int imm) {
        this.op = op;
        this.dest = dest;
        this.lhs = lhs;
        this.imm = imm;
    }
}
