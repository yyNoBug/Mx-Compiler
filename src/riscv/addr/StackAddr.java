package riscv.addr;

import riscv.RVFunction;
import riscv.register.REGISTER;

public class StackAddr extends Address {
    private RVFunction function;
    private int index;

    public StackAddr(RVFunction function, int index) {
        super(REGISTER.sp);
        this.function = function;
        this.index = index;
    }

    private void validate() {
        super.offset = function.getSize() - (index + 1) * 4;
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
