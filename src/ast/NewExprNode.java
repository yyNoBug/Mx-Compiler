package ast;

import java.util.List;

public class NewExprNode extends  ExprNode {
    private TypeNode newType;
    private int numDims;
    private List<ExprNode> dims;

    public NewExprNode(Location loc, TypeNode newType, List<ExprNode> dims, int numDims) {
        super(loc);
        this.newType = newType;
        this.dims = dims;
        this.numDims = numDims;
    }

    public TypeNode getNewType() {
        return newType;
    }

    public int getNumDims() {
        return numDims;
    }

    public List<ExprNode> getDims() {
        return dims;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
