package ast;

public class NumConstNode extends ExprNode {
    private int val;

    public NumConstNode(Location loc, int val) {
        super(loc);
        this.val = val;
    }

    public NumConstNode(Location loc, String str) {
        super(loc);
        this.val = Integer.parseInt(str);
    }

    public int getVal() {
        return val;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
