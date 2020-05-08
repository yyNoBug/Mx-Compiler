package scope;

import ast.ClassConstructorNode;
import ast.FunDeclNode;
import ast.VarDeclSingleNode;
import type.FuncType;
import type.NullType;
import type.Type;

import java.util.ArrayList;
import java.util.List;

public class DefinedFunction extends Entity {
    private List<DefinedVariable> parameters;
    private Type returnType;
    private Scope innerScope;
    private boolean isMemberFunction = false;
    private boolean isBuiltinFunction = false;

    public DefinedFunction(String name, Type returnType, List<DefinedVariable> parameters) {
        super(name, new FuncType());
        this.returnType = returnType;
        this.parameters = parameters;
        this.isBuiltinFunction = true;
    }

    public DefinedFunction(String name, Type returnType, List<DefinedVariable> parameters, boolean bool) {
        super(name, new FuncType());
        this.returnType = returnType;
        this.parameters = parameters;
        this.isMemberFunction = bool;
        this.isBuiltinFunction = true;
    }

    public DefinedFunction(FunDeclNode node, Scope parentScope) {
        super(node.getName(), new FuncType());
        this.innerScope = new LocalScope(parentScope);
        this.returnType = node.getType().getType();
        parameters = new ArrayList<>();
        for (VarDeclSingleNode varDeclSingleNode : node.getParameterList()) {
            DefinedVariable var = new DefinedVariable(varDeclSingleNode);
            varDeclSingleNode.setEntity(var);
            parameters.add(var);
            innerScope.defineVariable(var);
        }
    }

    public DefinedFunction(FunDeclNode node, Scope parentScope, boolean bool) {
        super(node.getName(), new FuncType());
        this.innerScope = new LocalScope(parentScope);
        this.returnType = node.getType().getType();
        parameters = new ArrayList<>();
        DefinedVariable thisPointer = new DefinedVariable("this", new NullType()); // Type here doesn't matter.
        parameters.add(thisPointer);
        innerScope.defineVariable(thisPointer);
        for (VarDeclSingleNode varDeclSingleNode : node.getParameterList()) {
            DefinedVariable var = new DefinedVariable(varDeclSingleNode);
            varDeclSingleNode.setEntity(var);
            parameters.add(var);
            innerScope.defineVariable(var);
        }
        isMemberFunction = bool;
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

    public boolean isMemberFunction() {
        return isMemberFunction;
    }
}
