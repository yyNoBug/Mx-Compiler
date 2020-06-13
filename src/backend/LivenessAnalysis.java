package backend;

import riscv.RVBlock;
import riscv.RVFunction;
import riscv.instruction.Instruction;
import riscv.register.REGISTER;

import java.util.HashMap;
import java.util.HashSet;

public class LivenessAnalysis {
    private RVFunction function;

    private HashMap<RVBlock, HashSet<REGISTER>> blockUses = new HashMap<>();
    private HashMap<RVBlock, HashSet<REGISTER>> blockDefs = new HashMap<>();
    private HashSet<RVBlock> visited = new HashSet<>();

    public LivenessAnalysis(RVFunction function) {
        this.function = function;
        deal();
    }

    private void deal() {
        function.getBlocks().forEach(this::visit);
        var exitBlock = function.getBlocks().get(function.getBlocks().size() - 1);
        iterateInOut(exitBlock);
    }

    private void visit(RVBlock block) {
        HashSet<REGISTER> uses = new HashSet<>();
        HashSet<REGISTER> defs = new HashSet<>();
        for (Instruction instruction : block.getInstructions()) {
            HashSet<REGISTER> instUses = instruction.getUses();
            instUses.removeAll(defs);
            uses.addAll(instUses);
            defs.addAll(instruction.getDefs());
        }
        //System.err.println("" + block + uses + defs);
        blockUses.put(block, uses);
        blockDefs.put(block, defs);
        block.liveIn = new HashSet<>();
        block.liveOut = new HashSet<>();
    }

    private void iterateInOut(RVBlock block) {
        if (visited.contains(block)) return;
        visited.add(block);
        HashSet<REGISTER> liveOut = new HashSet<>();
        for (RVBlock successor : block.successors) {
            liveOut.addAll(successor.liveIn);
        }
        HashSet<REGISTER> liveIn = new HashSet<>(liveOut);
        liveIn.removeAll(blockDefs.get(block));
        liveIn.addAll(blockUses.get(block));
        block.liveOut.addAll(liveOut);
        liveIn.removeAll(block.liveIn);
        if (!liveIn.isEmpty()) {
            block.liveIn.addAll(liveIn);
            visited.removeAll(block.precursors);
        }
        //System.err.println("pre:=====  " + block  + " " + block.precursors);
        for (RVBlock precursor : block.precursors) {
            iterateInOut(precursor);
        }
    }


}
