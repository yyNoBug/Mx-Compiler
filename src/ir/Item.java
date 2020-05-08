package ir;

abstract public class Item {
    private String name;

    public Item(String name) {
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
