package riscv;

import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.LinkedHashMap;
import java.util.Map;

public class RegisterAllocator {
    private RVTop top;
    private Map<VIRTUAL, REGISTER> virtualMap = new LinkedHashMap<>();
    private RVFunction curFunction;

    public RegisterAllocator(RVTop top) {
        this.top = top;
    }

    public void allocate() {
        var functions = top.getFunctions();
        for (RVFunction function : functions) {
            visit(function);
        }
    }

    private void visit(RVFunction function) {
        curFunction = function;
        for (RVBlock rvBlock : function.getBlocks()) {
            visit(rvBlock);
        }
    }

    private void visit(RVBlock block) {
        var inst_list = block.getInstructions();
        for (var itr = inst_list.listIterator(0); itr.hasNext();) {
            var inst = itr.next();
            inst.resolve(virtualMap, itr, curFunction);
        }
    }
}
