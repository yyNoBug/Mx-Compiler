package ir;

import java.util.ArrayList;

public class Function {
    private String name;
    private ArrayList<Block> blockList = new ArrayList<>();

    public Function(String name) {
        this.name = name;
    }

    public void add(Block thenBlock) {
        blockList.add(thenBlock);
    }

    @Override
    public String toString() {
        return name;
    }

    public void printIR() {
        for (Block block : blockList) {
            block.printIR();
        }
    }
}
