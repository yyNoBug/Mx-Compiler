package ir;

public class StoreStmt extends YyStmt {
    private Item src, dest;

    public StoreStmt(Item src, Item dest) {
        this.src = src;
        this.dest = dest;
    }

    @Override
    public String toString() {
        return "store " + src + " to " + dest;
    }
}
