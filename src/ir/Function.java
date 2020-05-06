package ir;

import java.util.ArrayList;

abstract public class Function {
    protected String name;
    protected ArrayList<Block> blockList = new ArrayList<>();

    public Function(String name) {
        this.name = name;
    }

    public void add(Block thenBlock) {
        blockList.add(thenBlock);
    }

    public ArrayList<Block> getBlockList() {
        return blockList;
    }

    @Override
    public String toString() {
        return name;
    }

    public void printIR() {
        for (Block block : blockList) {
            System.out.println("fun " + name + "()");
            block.printIR();
        }
    }
}
