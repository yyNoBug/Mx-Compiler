package ast;

public class VarDeclStatementNode extends StatementNode {
    private VarDeclListNode variable;

    public VarDeclStatementNode(VarDeclListNode variable, Location location) {
        super(location);
        this.variable = variable;
    }

    public VarDeclListNode getVariable() {
        return variable;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        // I assert the declaration is completely useless.
        visitor.visit(this);
        // System.err.println("How can that be ???");
    }
}
