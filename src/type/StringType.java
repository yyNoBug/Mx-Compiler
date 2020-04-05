package type;

import scope.DefinedClass;

public class StringType extends Type {
    static private DefinedClass entity;

    public static void setEntity(DefinedClass entity) {
        StringType.entity = entity;
    }

    public StringType() {
        type = Types.STRING;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof StringType;
    }

    @Override
    public DefinedClass getEntity() {
        return entity;
    }
}
