package ast;

public class StringConstNode extends ExprNode {
    private String str;

    public StringConstNode(Location loc, String str) {
        super(loc);
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
