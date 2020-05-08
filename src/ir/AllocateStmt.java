package ir;

public class AllocateStmt extends YyStmt {
    private Item item;

    public AllocateStmt(Item item) {
        this.item = item;
    }

    @Override
    public String toString() {
        return item + " = allocate";
    }
}
