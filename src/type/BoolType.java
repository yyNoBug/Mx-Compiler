package type;

public class BoolType extends Type {
    public BoolType() {
        type = Types.BOOL;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof BoolType;
    }
}
