package riscv;

import ir.Block;
import riscv.instruction.Instruction;

import java.util.ArrayList;
import java.util.List;

public class RVBlock {
    private String name;
    private List<Instruction> instructions = new ArrayList<>();

    public RVBlock(String name) {
        this.name = name;
    }

    public RVBlock(Block blk) {
        this.name = blk.getName();
    }

    public void add(Instruction inst) {
        instructions.add(inst);
    }
}
