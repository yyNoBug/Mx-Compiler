package ir;

abstract public class Register {
    private String name;

    public Register(String name) {
        this.name = name;
    }

    public int getNum() {
        return 0;
    }

    @Override
    public String toString() {
        return name;
    }
}
