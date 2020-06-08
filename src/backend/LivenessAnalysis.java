package backend;

import riscv.RVBlock;
import riscv.RVFunction;
import riscv.instruction.Instruction;
import riscv.register.REGISTER;

import java.util.HashMap;
import java.util.HashSet;

public class LivenessAnalysis {
    static HashMap<RVBlock, HashSet<REGISTER>> blockUses;
    static HashMap<RVBlock, HashSet<REGISTER>> blockDefs;
    static HashSet<RVBlock> visited;

    static void runForBlock(RVBlock block) {
        HashSet<REGISTER> uses = new HashSet<>();
        HashSet<REGISTER> defs = new HashSet<>();
        for (Instruction instruction : block.getInstructions()) {
            HashSet<REGISTER> instUses = instruction.getUses();
            instUses.removeAll(defs);
            uses.addAll(instUses);
            defs.addAll(instruction.getDefs());
        }
        System.err.println("" + block + uses + defs);
        blockUses.put(block, uses);
        blockDefs.put(block, defs);
        block.liveIn = new HashSet<>();
        block.liveOut = new HashSet<>();
    }


    static void runBackward(RVBlock block) {
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
            runBackward(precursor);
        }
    }

    public static void runForFunction(RVFunction function) {
        blockUses = new HashMap<>();
        blockDefs = new HashMap<>();
        visited = new HashSet<>();
        function.getBlocks().forEach(LivenessAnalysis::runForBlock);
        var exitBlock = function.getBlocks().get(function.getBlocks().size() - 1);
        runBackward(exitBlock);

//        System.err.println("==== blocks ====");
//        function.getBlocks().forEach(x -> {
//            System.err.println(x + "\nlive in:");
//            System.err.println(x.liveIn);
//            //x.liveIn.forEach(System.err::println);
//            System.err.println("live out:");
//            System.err.println(x.liveOut);
//        });
//        System.err.println("==== end =====");
         //TODO
    }
}
