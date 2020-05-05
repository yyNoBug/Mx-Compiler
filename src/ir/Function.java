package ir;

import java.util.ArrayList;

public class Function {
    private ArrayList<Block> blockList = new ArrayList<>();

    public void add(Block thenBlock) {
        blockList.add(thenBlock);
    }
}
