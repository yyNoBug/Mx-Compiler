package riscv;

import ir.Block;
import riscv.instruction.Instruction;
import riscv.register.REGISTER;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

public class RVBlock {
    private String name;
    private List<Instruction> instructions = new LinkedList<>();
    public List<RVBlock> precursors = new ArrayList<>();
    public List<RVBlock> successors = new ArrayList<>();
    public HashSet<REGISTER> liveIn = new HashSet<>();
    public HashSet<REGISTER> liveOut = new HashSet<>();

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

    public void printRV(PrintWriter writer){
        if (!name.equals("__invisible__"))
            writer.println(name + ":");
        for (Instruction instruction : instructions) {
            writer.println(instruction);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}
