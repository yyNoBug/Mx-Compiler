package ir;

public class AllocateStmt extends YyStmt {
    private Register item;

    public AllocateStmt(Register item) {
        this.item = item;
    }
}
