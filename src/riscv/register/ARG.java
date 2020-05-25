package riscv.register;

public class ARG extends REGISTER {
    private int num;

    @Override
    public String toString() {
        return "a"+num;
    }
}
