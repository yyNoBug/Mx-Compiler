package scope;


import type.FuncType;
import type.IntType;

import java.util.LinkedHashMap;
import java.util.Map;

public class TopLevelScope extends Scope {
    private TypeTable typeTable;
    private Map<String, Entity> entities = new LinkedHashMap<>();

    public TopLevelScope() {
        this.typeTable = new TypeTable(this);
    }

    public void defineClass(String className) throws SemanticException {
        typeTable.defineClass(className);
    }

    public void defineFunction(DefinedFunction function) {
        if (entities.containsKey(function.getName()))
            throw new SemanticException("Name " + function.getName() + "has been defined.");
        if (typeTable.hasType(function.getName()))
            throw new SemanticException("Name " + function.getName() + "has been defined.");
        entities.put(function.getName(), function);
    }

    public void defineVariable(DefinedVariable var) {
        if (entities.containsKey(var.getName()))
            throw new SemanticException("Name " + var.getName() + "has been defined.");
        if (typeTable.hasType(var.getName()))
            throw new SemanticException("Name " + var.getName() + "has been defined.");
        entities.put(var.getName(), var);
    }

    public Entity get(String name) throws SemanticException {
        Entity ent = entities.get(name);
        if (ent == null) {
            throw new SemanticException("Unsolved reference: " + name);
        }
        return ent;
    }

    public void checkMain() {
        if (!entities.containsKey("main"))
            throw new SemanticException("No main function");
        Entity main = entities.get("main");
        if (!(main.getType() instanceof FuncType))
            throw new SemanticException("Main not a function.");
        if (!(((DefinedFunction) main).getReturnType() instanceof IntType))
            throw new SemanticException("Return type of main function must be int.");
        if (!(((DefinedFunction) main).getParameters().isEmpty()))
            throw new SemanticException("Main function should not have parameters.");
    }

    public DefinedClass getClass(String name) {
        return typeTable.getClass(name);
    }
}
