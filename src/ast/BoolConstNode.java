package ast;

public class BoolConstNode extends ExprNode {
    private boolean val;

    public BoolConstNode(Location loc, boolean val) {
        super(loc);
        this.val = val;
    }

    public boolean getVal() {
        return val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
