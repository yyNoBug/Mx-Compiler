package riscv.addr;

import riscv.register.REGISTER;

public class Address {
    REGISTER base;
    int offset = 0;

    public Address(REGISTER base, int offset) {
        this.base = base;
        this.offset = offset;
    }

    public Address(REGISTER base) {
        this.base = base;
        this.offset = 0;
    }

    public REGISTER getBase() {
        return base;
    }

    public int getOffset() {
        return offset;
    }

    public void setBase(REGISTER base) {
        this.base = base;
    }

    @Override
    public String toString() {
        return offset + "(" + base + ")";
    }
}
