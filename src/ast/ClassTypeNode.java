package ast;

public class ClassTypeNode extends TypeNode {
    private String id;

    public ClassTypeNode(Location loc, String type, String id) {
        super(loc, type);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
