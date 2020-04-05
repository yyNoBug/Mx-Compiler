package type;

public class IntType extends Type {
    public IntType() {
        type = Types.INT;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof  IntType;
    }
}
