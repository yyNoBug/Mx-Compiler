package ast;

import java.util.List;

public class ClassDeclNode extends DeclarationNode {
    private String id;
    private List<VarDeclSingleNode> membersVars;
    private List<FunDeclNode> memberFuns;
    private ClassConstructorNode constructor;

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

    public List<VarDeclSingleNode> getMembersVars() {
        return membersVars;
    }

    public List<FunDeclNode> getMemberFuns() {
        return memberFuns;
    }

    public ClassConstructorNode getConstructor() {
        return constructor;
    }
}
