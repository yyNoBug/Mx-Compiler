package ast;

import scope.DefinedFunction;
import type.Type;

abstract public class ExprNode extends ASTNode {
    private Type type;
    private boolean isLeftValue;
    private DefinedFunction funcEntity = null;

    public ExprNode(Location loc) {
        super(loc);
    }

    public Type getType() {
        return type;
    }

    public boolean isLeftValue() {
        return isLeftValue;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setLeftValue(boolean leftValue) {
        isLeftValue = leftValue;
    }

    public void setFuncEntity(DefinedFunction funcEntity) {
        this.funcEntity = funcEntity;
    }

    public DefinedFunction getFuncEntity() {
        return funcEntity;
    }
}
