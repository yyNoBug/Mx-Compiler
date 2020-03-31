package ast;

public class VarDeclStatementNode extends StatementNode {
    private VarDeclListNode VarDeclarations;

    public VarDeclStatementNode(Location loc, VarDeclListNode varDeclarations) {
        super(loc);
        VarDeclarations = varDeclarations;
    }

    public VarDeclListNode getVarDeclarations() {
        return VarDeclarations;
    }
}
