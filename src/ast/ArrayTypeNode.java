package ast;

public class ArrayTypeNode extends TypeNode {
    private int dimension;

    public ArrayTypeNode(Location loc, String type, int dimension) {
        super(loc, type);
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }
}
