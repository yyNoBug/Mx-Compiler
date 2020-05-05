package ir;

public class Global extends Register {
    static private int globalCount = 0;

    public Global() {
        super("g" + globalCount);
        globalCount++;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
