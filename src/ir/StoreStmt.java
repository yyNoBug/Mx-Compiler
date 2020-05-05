package ir;

public class StoreStmt extends YyStmt {
    private Register src, dest;

    public StoreStmt(Register src, Register dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "store " + src + " to " + dest;
    }
}
