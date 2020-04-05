package ast;

public class ExprStatementNode extends StatementNode{
    private ExprNode expression;

    public ExprStatementNode(Location loc, ExprNode expression) {
        super(loc);
        this.expression = expression;
    }

    public ExprNode getExpression() {
        return expression;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}