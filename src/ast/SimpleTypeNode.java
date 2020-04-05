package ast;

import type.Type;

public class SimpleTypeNode extends TypeNode {
    public SimpleTypeNode(Location loc, Type type) {
        super(loc, type);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
