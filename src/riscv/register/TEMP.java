package riscv.register;

public class TEMP extends REGISTER {
    private int num;

    @Override
    public String toString() {
        return "t" + num;
    }
}
