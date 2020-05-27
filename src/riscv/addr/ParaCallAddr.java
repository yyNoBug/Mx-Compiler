package riscv.addr;

import riscv.register.REGISTER;

public class ParaCallAddr extends Address {
    //private RVFunction function;
    private int index;

    public ParaCallAddr(/*RVFunction function, */int index) {
        super(REGISTER.sp);
        //this.function = function;
        this.index = index;
    }

    private void validate() {
        super.offset = /*function.getSize() + */(index - 8) * 4;
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
