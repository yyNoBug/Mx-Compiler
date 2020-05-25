package ir.irStmt;

import ir.Block;
import ir.IRVisitor;

public class JmpStmt extends TerminalStmt {
    private Block destination;

    public JmpStmt(Block destination) {
        this.destination = destination;
    }

    public Block getDestination() {
        return destination;
    }

    @Override
    public String toString() {
        return "goto " + destination;
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
