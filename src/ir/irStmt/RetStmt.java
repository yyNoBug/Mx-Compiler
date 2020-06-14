package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

import java.util.HashMap;
import java.util.HashSet;

public class RetStmt extends TerminalStmt {
    private Item item;

    public RetStmt(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public Statement transform(HashMap<Item, Item> itemMap) {
        Item newItem = item;
        if (itemMap.containsKey(item)) newItem = itemMap.get(item);
        return new RetStmt(newItem);
    }

    @Override
    public Item getDef() {
        return null;
    }

    @Override
    public HashSet<Item> getUses() {
        return new HashSet<>();
    }

    @Override
    public String toString() {
        return "return " + item;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
