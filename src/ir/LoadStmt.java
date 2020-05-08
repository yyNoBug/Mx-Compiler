package ir;

public class LoadStmt extends YyStmt {
    private Item src;
    private Item dest;

    public LoadStmt(Item src, Item dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return dest + " = load " + src;
    }
}