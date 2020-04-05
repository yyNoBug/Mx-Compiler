package type;

public class FuncType extends Type {
    Type returnType;

    public FuncType() {
        type = Types.FUNC;
    }

    public FuncType(Type returnType) {
        type = Types.FUNC;
        this.returnType = returnType;
    }
}
