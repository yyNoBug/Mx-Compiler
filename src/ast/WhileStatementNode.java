package ast;

public class WhileStatementNode extends StatementNode {
    private ExprNode condition;
    private StatementNode body;

    public WhileStatementNode(Location loc, ExprNode condition, StatementNode body) {
        super(loc);
        this.condition = condition;
        this.body = body;
    }

    public ExprNode getCondition() {
        return condition;
    }

    public StatementNode getBody() {
        return body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
