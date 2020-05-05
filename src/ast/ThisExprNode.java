package ast;

import scope.DefinedClass;
import scope.DefinedVariable;

public class ThisExprNode extends ExprNode {

    private DefinedClass classEntity;
    private DefinedVariable entity;

    public ThisExprNode(Location loc) {
        super(loc);
    }

    public DefinedClass getClassEntity() {
        return classEntity;
    }

    public DefinedVariable getEntity() {
        return entity;
    }

    public void setClassEntity(DefinedClass classEntity) {
        this.classEntity = classEntity;
    }

    public void setEntity(DefinedVariable entity) {
        this.entity = entity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
