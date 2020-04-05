package ast;

import scope.Entity;

public class IdExprNode extends ExprNode {
    private String id;
    private Entity entity;

    public IdExprNode(Location loc, String id) {
        super(loc);
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public Entity getEntity() {
        return entity;
    }

    public void setEntity(Entity entity) {
        this.entity = entity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
