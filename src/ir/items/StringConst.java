package ir.items;

public class StringConst extends Item {
    private String str;

    static private int stringCount = 0;

    public StringConst(String str) {
        super("s" + stringCount++);
        this.str = str;
    }

    public String getStr() {
        return str;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
