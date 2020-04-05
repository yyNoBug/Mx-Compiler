package ast;

public class IfStatementNode extends StatementNode {
    private ExprNode condition;
    private StatementNode thenStatement, elseStatement;

    public IfStatementNode(Location loc, ExprNode condition, StatementNode thenStatement, StatementNode elseStatement) {
        super(loc);
        this.condition = condition;
        this.thenStatement = thenStatement;
        this.elseStatement = elseStatement;
    }

    public ExprNode getCondition() {
        return condition;
    }

    public StatementNode getThenStatement() {
        return thenStatement;
    }

    public StatementNode getElseStatement() {
        return elseStatement;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
