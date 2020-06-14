package ir.irStmt;

import ir.Block;
import ir.IRVisitor;
import ir.items.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class JmpStmt extends TerminalStmt {
    private Block destination;

    public JmpStmt(Block destination) {
        this.destination = destination;
    }

    public Block getDestination() {
        return destination;
    }

    @Override
    public Statement transform(HashMap<Item, Item> itemMap) {
        return new JmpStmt(destination);
    }

    @Override
    public void translateTarget(Map<Block, Block> map) {
        if (map.containsKey(destination)) destination = map.get(destination);
    }

    @Override
    public HashSet<Item> getUses() {
        return new HashSet<>();
    }

    @Override
    public String toString() {
        return "goto " + destination;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
