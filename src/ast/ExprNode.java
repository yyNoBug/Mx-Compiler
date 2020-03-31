package ast;

abstract public class ExprNode extends ASTNode {
    private String type;
    private boolean isLeftValue;

    public ExprNode(Location loc) {
        super(loc);
    }

    public String getType() {
        return type;
    }

    public boolean isLeftValue() {
        return isLeftValue;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setLeftValue(boolean leftValue) {
        isLeftValue = leftValue;
    }
}
