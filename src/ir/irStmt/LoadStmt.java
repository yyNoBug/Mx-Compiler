package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

import java.util.HashMap;
import java.util.HashSet;

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

    public void setSrc(Item src) {
        this.src = src;
    }

    @Override
    public Statement transform(HashMap<Item, Item> itemMap) {
        Item newSrc = src, newDest = dest;
        if (itemMap.containsKey(src)) newSrc = itemMap.get(src);
        if (itemMap.containsKey(dest)) newDest = itemMap.get(dest);
        return new LoadStmt(newSrc, newDest);
    }

    @Override
    public Item getDef() {
        return dest;
    }

    @Override
    public HashSet<Item> getUses() {
        return new HashSet<>() {{
            add(src);
        }};
    }

    @Override
    public String toString() {
        return dest + " = load " + src;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}