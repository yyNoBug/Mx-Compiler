package riscv.register;

public class ARG extends REGISTER {
    private int num;

    public ARG(int num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "a" + num;
    }
}
