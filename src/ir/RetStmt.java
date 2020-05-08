package ir;

public class RetStmt extends TerminalStmt {
    private Item item;

    public RetStmt(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return "return " + item;
    }
}
