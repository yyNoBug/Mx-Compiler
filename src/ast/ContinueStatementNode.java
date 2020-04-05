package ast;

public class ContinueStatementNode extends StatementNode {
    public ContinueStatementNode(Location loc) {
        super(loc);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
