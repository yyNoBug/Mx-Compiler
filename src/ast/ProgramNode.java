package ast;

import java.util.List;

public class ProgramNode extends ASTNode {
    private List<DeclarationNode> section_list;

    public ProgramNode(Location loc, List<DeclarationNode> section_list) {
        super(loc);
        this.section_list = section_list;
    }

    public List<DeclarationNode> getSection_list() {
        return section_list;
    }
}
