package ir;

public class Local extends Item {
    static private int localCount = 0;
    private int num;

    public Local() {
        super("v" + localCount);
        num = localCount;
        localCount++;
    }

    @Override
    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
