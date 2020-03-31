package ast;

import java.util.List;

public class FunCallExprNode extends ExprNode {
    private ExprNode name;
    private List<ExprNode> parameterList;

    public FunCallExprNode(Location loc, ExprNode function, List<ExprNode> parameterList) {
        super(loc);
        this.name = function;
        this.parameterList = parameterList;
    }

    public ExprNode getName() {
        return name;
    }

    public List<ExprNode> getParameterList() {
        return parameterList;
    }
}
