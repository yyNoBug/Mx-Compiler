package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

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
