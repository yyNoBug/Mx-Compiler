package ir.items;

public class NumConst extends Item {
    private int value;

    public NumConst(int value) {
        super("Constant:" + value);
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value + "";
    }
}
