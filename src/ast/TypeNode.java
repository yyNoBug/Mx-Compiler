package ast;

import type.Type;

abstract public class TypeNode extends ASTNode {
    private Type type;

    public TypeNode(Location loc, Type type) {
        super(loc);
        this.type = type;
    }

    public Type getType() {
        return type;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
