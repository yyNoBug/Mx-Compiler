package scope;

import java.util.ArrayList;
import java.util.List;

abstract public class Scope {
    protected List<LocalScope> children = new ArrayList<>();

    public abstract Entity get(String name) throws SemanticException;
    public abstract void defineFunction(DefinedFunction function);
    public abstract void defineVariable(DefinedVariable var);
}
