package ast;

abstract public class TypeNode extends ASTNode {
    private String type;

    public TypeNode(Location loc, String type) {
        super(loc);
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
