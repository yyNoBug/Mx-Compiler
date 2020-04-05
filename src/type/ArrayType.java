package type;

import scope.DefinedClass;
import scope.TopLevelScope;

public class ArrayType extends Type {
    private Type baseType;
    private int numDims;
    static private DefinedClass entity;

    public ArrayType(Type baseType, int numDims) {
        this.baseType = baseType;
        this.numDims = numDims;
    }

    public Type getBaseType() {
        return baseType;
    }

    public int getNumDims() {
        return numDims;
    }

    public static void setEntity(DefinedClass entity) {
        ArrayType.entity = entity;
    }

    @Override
    public void resolve(TopLevelScope globalScope) {
        super.resolve(globalScope);
        baseType.resolve(globalScope);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof ArrayType &&
                ((ArrayType) obj).baseType == baseType && ((ArrayType) obj).numDims == numDims;
    }

    @Override
    public DefinedClass getEntity() {
        return entity;
    }
}
