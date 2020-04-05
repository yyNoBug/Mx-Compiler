package ast;

public class BreakStatementNode extends StatementNode {
    public BreakStatementNode(Location loc) {
        super(loc);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
