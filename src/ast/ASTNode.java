package ast;

abstract public class ASTNode {
    protected Location location;

    public ASTNode(Location loc) {
        location = loc;
    }

    public Location getLocation() {
        return location;
    }

    abstract public void accept(ASTVisitor visitor);
}