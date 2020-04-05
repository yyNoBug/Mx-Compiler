package ast;

import java.util.List;

public class ClassConstructorNode extends FunDeclNode {
    public ClassConstructorNode(Location loc, TypeNode type, String name,
                                List<VarDeclSingleNode> parameterList, StatementBlockNode body) {
        super(loc, type, name, parameterList, body);
    }

    @Override
    public void accept(ASTVisitor visitor) {
        visitor.visit(this);
    }
}
