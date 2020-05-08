package scope;

import type.Type;

import java.util.LinkedHashMap;
import java.util.Map;

public class DefinedClass extends Entity {
    private Scope innerScope;
    private DefinedFunction constructor;
    private Map<String, DefinedVariable> variableMap = new LinkedHashMap<>();
    private Map<String, DefinedFunction> functionMap = new LinkedHashMap<>();
    private int varCounter = 0;
    private Map<String, Integer> variableCountMap = new LinkedHashMap<>();

    public DefinedClass(String name, Type type, Scope innerScope) {
        super(name, type);
        this.innerScope = innerScope;
    }

    public void defineMemberVariable(DefinedVariable var) {
        if (variableMap.containsKey(var.getName()) || functionMap.containsKey(var.getName()))
            throw new SemanticException("Name "+ var.getName() + "has been defined.");
        variableMap.put(var.getName(), var);
        variableCountMap.put(var.getName(), varCounter++);
    }

    public void defineMemberFunction(DefinedFunction function) {
        if (variableMap.containsKey(function.getName()) || functionMap.containsKey(function.getName()))
            throw new SemanticException("Name "+ function.getName() + "has been defined.");
        functionMap.put(function.getName(), function);
    }

    public void defineConstructor(DefinedFunction function) {
        constructor = function;
    }

    public Entity resolveMember(String str) {
        if (variableMap.containsKey(str)) return variableMap.get(str);
        if (functionMap.containsKey(str)) return functionMap.get(str);
        throw new SemanticException("Member not defined!");
    }

    public Scope getInnerScope() {
        return innerScope;
    }

    public DefinedFunction getConstructor() {
        return constructor;
    }

    @Override
    public int calOffset(String member) {
        return variableCountMap.get(member);
    }

    public int getClassSize() {
        return varCounter * 4;
    }
}
