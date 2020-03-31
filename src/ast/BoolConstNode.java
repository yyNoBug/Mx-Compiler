package ast;

public class BoolConstNode extends ExprNode {
    private boolean bool;

    public BoolConstNode(Location loc, boolean bool) {
        super(loc);
        this.bool = bool;
    }

    public boolean isBool() {
        return bool;
    }
}
