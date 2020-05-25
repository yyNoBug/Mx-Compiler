package ir.irStmt;

import ir.Block;
import ir.IRVisitor;
import ir.items.Item;

public class BranchStmt extends TerminalStmt {
    private Item condition;
    private Block thenBlock;
    private Block elseBlock;

    public BranchStmt(Item condition, Block thenBlock, Block elseBlock) {
        this.condition = condition;
        this.thenBlock = thenBlock;
        this.elseBlock = elseBlock;
    }

    public Item getCondition() {
        return condition;
    }

    public Block getThenBlock() {
        return thenBlock;
    }

    public Block getElseBlock() {
        return elseBlock;
    }

    @Override
    public String toString() {
        return "branch " + condition + " (true -> " + thenBlock + ") (false -> " + elseBlock + ")";
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
