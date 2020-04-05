package type;

public class VoidType extends Type {
    public VoidType() {
        type = Types.VOID;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof VoidType;
    }
}
