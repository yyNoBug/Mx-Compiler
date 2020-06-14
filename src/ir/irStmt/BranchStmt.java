package ir.irStmt;

import ir.Block;
import ir.IRVisitor;
import ir.items.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

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

    public void setCondition(Item condition) {
        this.condition = condition;
    }

    @Override
    public Statement transform(HashMap<Item, Item> itemMap) {
        Item newCondition = condition;
        if (itemMap.containsKey(condition)) newCondition = itemMap.get(condition);
        return new BranchStmt(newCondition, thenBlock, elseBlock);
    }

    @Override
    public void translateTarget(Map<Block, Block> map) {
        if (map.containsKey(thenBlock)) thenBlock = map.get(thenBlock);
        if (map.containsKey(elseBlock)) elseBlock = map.get(elseBlock);
    }

    @Override
    public Item getDef() {
        return null;
    }

    @Override
    public HashSet<Item> getUses() {
        return new HashSet<>() {{
            add(condition);
        }};
    }

    @Override
    public String toString() {
        return "branch " + condition + " (true -> " + thenBlock + ") (false -> " + elseBlock + ")";
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
