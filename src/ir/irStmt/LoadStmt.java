package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

public class LoadStmt extends YyStmt {
    private Item src;
    private Item dest;

    public LoadStmt(Item src, Item dest) {
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
        return dest + " = load " + src;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}