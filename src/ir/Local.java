package ir;

public class Local extends Register {
    static private int localCount = 0;

    public Local() {
        super("v" + localCount);
        localCount++;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
