package ir;

public class NumConst extends Register {
    private int value;

    public NumConst(int value) {
        super("Constant:" + value);
        this.value = value;
    }
}
