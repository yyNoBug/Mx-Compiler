package riscv;

import ir.items.StringConst;

public class RVString {
    String name;
    int length;
    String asmForm;

    public RVString(StringConst str) {
        name = "string" + str.getNum();
    }

    private String getEscapedString() {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < name.length(); ++i) {
            switch (name.charAt(i)) {
                case '\n': strBuilder.append("\\\n"); break;
                case '\\': strBuilder.append("\\\\"); break;
                case '\t': strBuilder.append("\\\t"); break;
                case '\"': strBuilder.append("\\\""); break;
                default: strBuilder.append(name.charAt(i));
            }
        }
        return strBuilder.toString();
    }

    @Override
    public String toString() {
        return getEscapedString();
    }
}
