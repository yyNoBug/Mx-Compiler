package ast;

public class ContinueStatementNode extends StatementNode {
    private ASTNode loopNode;

    public ContinueStatementNode(Location loc) {
        super(loc);
    }

    public ASTNode getLoopNode() {
        return loopNode;
    }

    public void setLoopNode(ASTNode loopNode) {
        this.loopNode = loopNode;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
