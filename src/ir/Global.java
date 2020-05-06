package ir;

public class Global extends Register {
    static private int globalCount = 0;
    private int num;

    public Global() {
        super("g" + globalCount);
        num = globalCount;
        globalCount++;
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
