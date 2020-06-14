package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

import java.util.HashMap;
import java.util.HashSet;

public class AllocateStmt extends YyStmt {
    private Item item;

    public AllocateStmt(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public Statement transform(HashMap<Item, Item> itemMap) {
        return null;
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
        return item + " = allocate";
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
