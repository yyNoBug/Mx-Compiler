package type;

public class NullType extends Type {
    public NullType() {
        type = Types.NULL;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof NullType;
    }
}
