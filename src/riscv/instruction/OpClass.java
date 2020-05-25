package riscv.instruction;

public class OpClass {
    public enum Op{
        ADD, SUB, SLT, AND, OR, XOR, SLL, SRL, SRA, MUL, DIV, REM
    }

    public enum Cmp{
        EQ, NEQ, LT, GT
    }
}
