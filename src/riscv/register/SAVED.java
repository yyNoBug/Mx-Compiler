package riscv.register;

public class SAVED extends REGISTER {
    private int num;

    @Override
    public String toString() {
        return "s" + num;
    }
}
