package ir;

public class StoreStmt extends YyStmt {
    private Register src, dest;

    public StoreStmt(Register src, Register dest) {
        this.src = src;
        this.dest = dest;
    }
}
