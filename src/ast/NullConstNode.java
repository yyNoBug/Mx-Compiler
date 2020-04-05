package ast;

public class NullConstNode extends ExprNode {
    public NullConstNode(Location loc) {
        super(loc);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
