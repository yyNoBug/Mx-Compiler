package ast;

public class IdExprNode extends ExprNode {
    private String id;

    public IdExprNode(Location loc, String id) {
        super(loc);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
