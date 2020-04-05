package ast;

import scope.DefinedClass;

public class ThisExprNode extends ExprNode {

    private DefinedClass classEntity;

    public ThisExprNode(Location loc) {
        super(loc);
    }

    public DefinedClass getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(DefinedClass classEntity) {
        this.classEntity = classEntity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
