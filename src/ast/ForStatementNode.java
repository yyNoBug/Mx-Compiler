package ast;

public class ForStatementNode extends StatementNode {
    private ExprNode init, cond, step;
    private StatementNode statement;

    public ForStatementNode(Location loc, ExprNode init, ExprNode cond, ExprNode step, StatementNode statement) {
        super(loc);
        this.init = init;
        this.cond = cond;
        this.step = step;
        this.statement = statement;
    }

    public ExprNode getInit() {
        return init;
    }

    public ExprNode getCond() {
        return cond;
    }

    public ExprNode getStep() {
        return step;
    }

    public StatementNode getStatement() {
        return statement;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
