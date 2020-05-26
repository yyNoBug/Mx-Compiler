package riscv.register;

public class SAVED extends REGISTER {
    private int num;

    public SAVED(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "s" + num;
    }
}
