package ir.irStmt;

import ir.Block;
import ir.IRVisitor;
import ir.items.Item;

import java.util.HashSet;

public class JmpStmt extends TerminalStmt {
    private Block destination;

    public JmpStmt(Block destination) {
        this.destination = destination;
    }

    public Block getDestination() {
        return destination;
    }

    @Override
    public HashSet<Item> getUses() {
        return new HashSet<>();
    }

    @Override
    public String toString() {
        return "goto " + destination;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
