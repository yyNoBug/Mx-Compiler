package riscv;

import ir.items.StringConst;

public class RVString {
    String name;
    int length;
    String asmForm;

    public RVString(StringConst str) {
        name = "string" + str.getNum();
    }

    @Override
    public String toString() {
        return name;
    }
}
