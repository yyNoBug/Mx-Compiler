package ir;

public class BranchStmt extends TerminalStmt {
    private Register condition;
    private Block thenBlock;
    private Block elseBlock;

    public BranchStmt(Register condition, Block thenBlock, Block elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    @Override
    public String toString() {
        return "branch " + condition + " (true -> " + thenBlock + ") (false -> " + elseBlock + ")";
    }
}
