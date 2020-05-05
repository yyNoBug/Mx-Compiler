package ir;

public class JmpStmt extends TerminalStmt {
    private Block destination;

    public JmpStmt(Block destination) {
        this.destination = destination;
    }

    @Override
    public String toString() {
        return "goto " + destination;
    }
}
