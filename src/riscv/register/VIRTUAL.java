package riscv.register;

public class VIRTUAL extends REGISTER {
    static public int cnt = 0;

    private int num;

    public VIRTUAL() {
        num = cnt++;
    }

    @Override
    public String toString() {
        return "v" + num;
    }
}
