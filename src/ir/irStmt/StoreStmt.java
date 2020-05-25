package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

public class StoreStmt extends YyStmt {
    private Item src, dest;

    public StoreStmt(Item src, Item dest) {
        this.src = src;
        this.dest = dest;
    }

    public Item getSrc() {
        return src;
    }

    public Item getDest() {
        return dest;
    }

    @Override
    public String toString() {
        return "store " + src + " to " + dest;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
