package riscv;

import riscv.register.REGISTER;

public class RVAddress {
    REGISTER base;
    int delta = 0;

    public class Stack {
        RVFunction function;
        int index;
    }
}
