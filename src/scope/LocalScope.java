package scope;

import java.util.LinkedHashMap;
import java.util.Map;

public class LocalScope extends Scope {
    protected Scope parent;
    protected Map<String, Entity> entities = new LinkedHashMap<>();

    public LocalScope(Scope parent) {
        this.parent = parent;
        this.parent.children.add(this);
    }

    public Entity get(String name) throws SemanticException {
        Entity var = entities.get(name);
        if (var != null) return var;
        else return parent.get(name);
    }

    public void defineFunction(DefinedFunction function) {
        if (entities.containsKey(function.getName()))
            throw new SemanticException("Name " + function.getName() + "has been defined.");
        entities.put(function.getName(), function);
    }

    public void defineVariable(DefinedVariable var) {
        if (entities.containsKey(var.getName()))
            throw new SemanticException("Name " + var.getName() + "has been defined.");
        entities.put(var.getName(), var);
    }
}
