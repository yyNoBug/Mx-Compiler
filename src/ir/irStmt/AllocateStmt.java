package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

public class AllocateStmt extends YyStmt {
    private Item item;

    public AllocateStmt(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return item + " = allocate";
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
