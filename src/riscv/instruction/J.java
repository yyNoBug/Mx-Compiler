package riscv.instruction;

import riscv.RVBlock;

public class J extends Instruction {
    private RVBlock dest;

    public J(RVBlock dest) {
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "\tj\t" + dest;
    }
}
