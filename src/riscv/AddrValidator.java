package riscv;

public class AddrValidator {
    private RVTop top;

    public AddrValidator(RVTop top) {
        this.top = top;
    }

    public void validate() {
        var functions = top.getFunctions();
        for (RVFunction function : functions) {
            visit(function);
        }
    }

    private void visit(RVFunction function) {
        for (RVBlock rvBlock : function.getBlocks()) {
            visit(rvBlock);
        }
    }

    private void visit(RVBlock block) {
        var inst_list = block.getInstructions();
        for (var itr = inst_list.listIterator(0); itr.hasNext();) {
            var inst = itr.next();
            inst.addrValidate(itr);
        }
    }
}
