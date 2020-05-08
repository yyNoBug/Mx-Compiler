package ast;

import scope.DefinedClass;

import java.util.List;

public class ClassDeclNode extends DeclarationNode {
    private String id;
    private List<VarDeclSingleNode> membersVars;
    private List<FunDeclNode> memberFuns;
    private ClassConstructorNode constructor;
    private DefinedClass entity;

    public ClassDeclNode(Location loc, String id, List<VarDeclSingleNode> membersVars, List<FunDeclNode> memberFuns,
                         ClassConstructorNode constructor) {
        super(loc);
        this.id = id;
        this.membersVars = membersVars;
        this.memberFuns = memberFuns;
        this.constructor = constructor;
    }

    public String getId() {
        return id;
    }

    public List<VarDeclSingleNode> getMemberVars() {
        return membersVars;
    }

    public List<FunDeclNode> getMemberFuns() {
        return memberFuns;
    }

    public ClassConstructorNode getConstructor() {
        return constructor;
    }

    public DefinedClass getEntity() {
        return entity;
    }

    public void setEntity(DefinedClass entity) {
        this.entity = entity;
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
