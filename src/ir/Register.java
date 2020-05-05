package ir;

abstract public class Register {
    private String name;

    public Register(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
