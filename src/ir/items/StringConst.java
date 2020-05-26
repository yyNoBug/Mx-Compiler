package ir.items;

public class StringConst extends Item {
    private String str;
    private int number;

    static private int stringCount = 0;

    public StringConst(String str) {
        super("s" + stringCount);
        number = stringCount++;
        this.str = str;
    }

    public int getNumber() {
        return number;
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
