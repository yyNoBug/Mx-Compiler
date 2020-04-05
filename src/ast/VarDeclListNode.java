package ast;

import java.util.List;

public class VarDeclListNode extends ASTNode {
    private TypeNode type;
    private List<VarDeclSingleNode> variables;

    public VarDeclListNode(Location loc, TypeNode type, List<VarDeclSingleNode> variables) {
        super(loc);
        this.type = type;
        this.variables = variables;
    }

    public TypeNode getType() {
        return type;
    }

    public List<VarDeclSingleNode> getVariables() {
        return variables;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        System.out.println("How can that be ???");
    }
}
