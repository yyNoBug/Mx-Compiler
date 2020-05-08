package ir;

public class BranchStmt extends TerminalStmt {
    private Item condition;
    private Block thenBlock;
    private Block elseBlock;

    public BranchStmt(Item condition, Block thenBlock, Block elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return "branch " + condition + " (true -> " + thenBlock + ") (false -> " + elseBlock + ")";
    }
}
