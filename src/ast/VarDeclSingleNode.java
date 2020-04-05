package ast;

public class VarDeclSingleNode extends DeclarationNode {
    private TypeNode type;
    private String id;
    private ExprNode expr;

    public VarDeclSingleNode(Location loc, TypeNode type, String id, ExprNode expr) {
        super(loc);
        this.type = type;
        this.id = id;
        this.expr = expr;
    }

    public TypeNode getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    public ExprNode getExpr() {
        return expr;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
