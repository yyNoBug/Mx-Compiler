package ir;

public class StringConst extends Register {
    private String str;

    static private int stringCount = 0;

    public StringConst(String str) {
        super("s" + stringCount++);
        this.str = str;
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
