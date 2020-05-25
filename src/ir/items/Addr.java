package ir.items;

public class Addr extends Item {
    static private int addrCount = 0;
    private int num;

    public Addr() {
        super("a" + addrCount);
        num = addrCount;
        addrCount++;
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
