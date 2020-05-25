package riscv;

import ir.DeclaredFunction;

import java.util.ArrayList;

public class RVFunction {
    private String name;
    private ArrayList<RVBlock> RVBlocks = new ArrayList<>();
    private int top, bottom;

    public RVFunction(DeclaredFunction function) {
        this.name = function.getName();
        top = 0;
        bottom = 0;
    }

    public void add(RVBlock blk) {
        RVBlocks.add(blk);
    }

    public int getSize() {
        return (top + bottom) * 4;
    }

    public int getTopIndex(){
        return top++;
    }
}
