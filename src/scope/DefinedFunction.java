package scope;

import ast.ClassConstructorNode;
import ast.FunDeclNode;
import ast.VarDeclSingleNode;
import type.FuncType;
import type.Type;

import java.util.ArrayList;
import java.util.List;

public class DefinedFunction extends Entity {
    private List<DefinedVariable> parameters;
    private Type returnType;
    private Scope innerScope;

    public DefinedFunction(String name, Type returnType, List<DefinedVariable> parameters) {
        super(name, new FuncType());
        this.returnType = returnType;
        this.parameters = parameters;
    }

    public DefinedFunction(FunDeclNode node, Scope parentScope) {
        super(node.getName(), new FuncType());
        this.innerScope = new LocalScope(parentScope);
        this.returnType = node.getType().getType();
        parameters = new ArrayList<>();
        for (VarDeclSingleNode varDeclSingleNode : node.getParameterList()) {
            DefinedVariable var = new DefinedVariable(varDeclSingleNode);
            parameters.add(var);
            innerScope.defineVariable(var);
        }
    }

    public DefinedFunction(ClassConstructorNode node) {
        super(node.getName(), new FuncType());
        parameters = new ArrayList<>();
        type = node.getType().getType();
    }

    public Type getReturnType() {
        return returnType;
    }

    public Scope getInnerScope() {
        return innerScope;
    }

    public List<DefinedVariable> getParameters() {
        return parameters;
    }
}
