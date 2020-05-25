package ir.irStmt;

import ir.IRVisitor;
import ir.items.Item;

public class RetStmt extends TerminalStmt {
    private Item item;

    public RetStmt(Item item) {
        this.item = item;
    }

    public Item getItem() {
        return item;
    }

    @Override
    public String toString() {
        return "return " + item;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
