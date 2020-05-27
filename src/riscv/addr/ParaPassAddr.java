package riscv.addr;

import riscv.register.REGISTER;

public class ParaPassAddr extends Address {
    private int index;

    public ParaPassAddr(int index) {
        super(REGISTER.sp);
        this.index = index;
    }

    private void validate() {
        super.offset = (index - 8) * 4;
    }

    @Override
    public int getOffset() {
        validate();
        return super.getOffset();
    }

    @Override
    public String toString() {
        validate();
        return super.toString();
    }
}
