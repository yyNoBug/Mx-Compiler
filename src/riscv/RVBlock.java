package riscv;

import ir.Block;
import riscv.instruction.Instruction;

import java.util.LinkedList;
import java.util.List;

public class RVBlock {
    private String name;
    private List<Instruction> instructions = new LinkedList<>();

    public RVBlock(String name) {
        this.name = name;
    }

    public RVBlock(Block blk) {
        this.name = "." + blk.getName();
    }

    public List<Instruction> getInstructions() {
        return instructions;
    }

    public void add(Instruction inst) {
        instructions.add(inst);
    }

    public void printRV(){
        if (!name.equals("__invisible__"))
            System.out.println(name + ":");
        for (Instruction instruction : instructions) {
            System.out.println(instruction);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
