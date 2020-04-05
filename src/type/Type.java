package type;

import scope.DefinedClass;
import scope.TopLevelScope;

abstract public class Type {
    public enum Types {
        VOID, INT, BOOL, STRING, CLASS, ARRAY, NULL, FUNC
    }

    Types type;

    public Types getTypes() {
        return type;
    }

    public void resolve(TopLevelScope globalScope) {

    }

    public DefinedClass getEntity() {
        return null;
    }

    public boolean compacts(Type other) {
        return this.equals(other);
    }
}
