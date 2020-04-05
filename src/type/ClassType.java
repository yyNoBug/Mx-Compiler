package type;

import scope.DefinedClass;
import scope.TopLevelScope;

public class ClassType extends Type {
    private String name;
    private DefinedClass entity = null;

    public ClassType(String name) {
        this.name = name;
        type = Types.CLASS;
    }

    public String getName() {
        return name;
    }

    public void setEntity(DefinedClass entity) {
        this.entity = entity;
    }

    @Override
    public void resolve(TopLevelScope globalScope) {
        super.resolve(globalScope);
        entity = globalScope.getClass(name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ClassType && name.equals(((ClassType) obj).name);
    }

    @Override
    public boolean compacts(Type other) {
        return this.equals(other) || (other instanceof NullType);
    }

    @Override
    public DefinedClass getEntity() {
        return entity;
    }
}
