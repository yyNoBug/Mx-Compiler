package riscv.register;

public class VIRTUAL extends REGISTER {
    static public int cnt = 0;

    private int num;

    public VIRTUAL() {
        num = 0;
    }

    public VIRTUAL(int num) {
        this.num = num;
    }
}
