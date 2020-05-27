package ir;

import java.io.PrintWriter;
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

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    public void printIR(PrintWriter writer) {
        for (Block block : blockList) {
            writer.println("fun " + name + "()");
            block.printIR(writer);
        }
    }
}
