package ast;

public class ArrayExprNode extends ExprNode {
    private ExprNode array, index;

    public ArrayExprNode(Location loc, ExprNode array, ExprNode index) {
        super(loc);
        this.array = array;
        this.index = index;
    }

    public ExprNode getArray() {
        return array;
    }

    public ExprNode getIndex() {
        return index;
    }


    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
