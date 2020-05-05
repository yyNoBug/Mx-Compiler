package scope;

import type.Type;

abstract public class Entity {
    private String name;
    protected Type type;
    private boolean type_resolved = false;

    public Entity(String name, Type type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public void resolveType(TopLevelScope globalScope) {
        type.resolve(globalScope);
    }

    public int calOffset(String member){
        return 0;
    }
}
