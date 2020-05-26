package riscv;

import ir.items.Global;

public class RVGlobal {
    String name;

    public RVGlobal(String name) {
        this.name = name;
    }

    public RVGlobal(Global global) {
        this.name = "global" + global.getNum();
    }

    @Override
    public String toString() {
        return name;
    }
}
