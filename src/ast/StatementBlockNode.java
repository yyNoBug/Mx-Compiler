package ast;

import java.util.List;

public class StatementBlockNode extends StatementNode {
    private List<StatementNode> stmList;

    public StatementBlockNode(Location loc, List<StatementNode> stmList) {
        super(loc);
        this.stmList = stmList;
    }

    public List<StatementNode> getStmList() {
        return stmList;
    }
}
