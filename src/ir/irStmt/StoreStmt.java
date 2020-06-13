package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

import java.util.HashSet;

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

    public void setSrc(Item src) {
        this.src = src;
    }

    public void setDest(Item dest) {
        this.dest = dest;
    }

    @Override
    public HashSet<Item> getUses() {
        return new HashSet<>() {{
            add(src);
            add(dest);
        }};
    }

    @Override
    public String toString() {
        return "store " + src + " to " + dest;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
