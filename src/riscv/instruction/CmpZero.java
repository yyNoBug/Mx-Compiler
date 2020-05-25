package riscv.instruction;

import riscv.register.REGISTER;

class CmpZero extends Instruction {
    private OpClass.Cmp op;
    private REGISTER reg, dest;
}
