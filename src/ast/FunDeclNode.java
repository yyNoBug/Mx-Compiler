package ast;

import java.util.List;

public class FunDeclNode extends DeclarationNode {
    private TypeNode type;
    private String name;
    private List<VarDeclSingleNode> parameterList;
    private StatementBlockNode body;

    public FunDeclNode(Location loc, TypeNode type, String name, List<VarDeclSingleNode> parameterList, StatementBlockNode body) {
        super(loc);
        this.type = type;
        this.name = name;
        this.parameterList = parameterList;
        this.body = body;
    }

    public TypeNode getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public List<VarDeclSingleNode> getParameterList() {
        return parameterList;
    }

    public StatementBlockNode getBody() {
        return body;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
