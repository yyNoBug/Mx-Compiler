package ast;

import scope.DefinedVariable;

public class VarDeclSingleNode extends DeclarationNode {
    private TypeNode type;
    private String id;
    private ExprNode expr;
    private DefinedVariable entity;

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

    public DefinedVariable getEntity() {
        return entity;
    }

    public void setEntity(DefinedVariable entity) {
        this.entity = entity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
