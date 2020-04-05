package ast;

import scope.Scope;

import java.util.List;

public class StatementBlockNode extends StatementNode {
    private List<ASTNode> stmList;
    private Scope scope;

    public StatementBlockNode(Location loc, List<ASTNode> stmList) {
        super(loc);
        this.stmList = stmList;
    }

    public List<ASTNode> getStmList() {
        return stmList;
    }

    public Scope getScope() {
        return scope;
    }

    public void setScope(Scope scope) {
        this.scope = scope;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
