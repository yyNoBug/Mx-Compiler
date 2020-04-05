package scope;

import ast.VarDeclSingleNode;
import type.Type;

public class DefinedVariable extends Entity {
    public DefinedVariable(String name, Type type) {
        super(name, type);
    }

    public DefinedVariable(VarDeclSingleNode node) {
        super(node.getId(), node.getType().getType());
    }
}
