package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

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
