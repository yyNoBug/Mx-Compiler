package ast;

import type.ArrayType;
import type.Type;

public class ArrayTypeNode extends TypeNode {
    private Type baseType;
    private int dimension;

    public ArrayTypeNode(Location loc, Type type, int dimension) {
        super(loc, new ArrayType(type, dimension));
        this.dimension = dimension;
        this.baseType = type;
    }

    public Type getBaseType() {
        return baseType;
    }

    public int getDimension() {
        return dimension;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
