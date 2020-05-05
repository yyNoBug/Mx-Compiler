package ir;

public class LoadStmt extends YyStmt {
    private Register src;
    private Register dest;

    public LoadStmt(Register src, Register dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return dest + " = load " + src;
    }
}