package ir.items;

public class StringConst extends Item {
    private String str;
    private int num;

    static private int stringCount = 0;

    public StringConst(String str) {
        super("s" + stringCount);
        num = stringCount++;
        this.str = str;
    }

    @Override
    public int getNum() {
        return num;
    }

    private String getEscapedString() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            switch (str.charAt(i)) {
                case '\n': strBuilder.append("\\\n"); break;
                case '\\': strBuilder.append("\\\\"); break;
                case '\t': strBuilder.append("\\\t"); break;
                case '\"': strBuilder.append("\\\""); break;
                default: strBuilder.append(str.charAt(i));
            }
        }
        return strBuilder.toString();
    }

    public String getStr() {
        return getEscapedString();
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
