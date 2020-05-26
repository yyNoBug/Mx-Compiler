package riscv.register;

public class TEMP extends REGISTER {
    private int num;

    public TEMP(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "t" + num;
    }
}
