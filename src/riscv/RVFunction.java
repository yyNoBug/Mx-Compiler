package riscv;

import ir.DeclaredFunction;

import java.util.ArrayList;

public class RVFunction {
    private String name;
    private ArrayList<RVBlock> blocks = new ArrayList<>();
    private int top, bottom;

    public RVFunction(DeclaredFunction function) {
        this.name = function.getName();
        top = 0;
        bottom = 0;
    }

    public RVFunction(String name) {
        this.name = name;
    }

    public void add(RVBlock blk) {
        blocks.add(blk);
    }

    public int getSize() {
        return ((top + bottom) * 4 - 1) / 16 * 16 + 16;
    }

    public int getTopIndex(){
        return top++;
    }

    public ArrayList<RVBlock> getBlocks() {
        return blocks;
    }

    public void setBottom(int bottom) {
        if (bottom > this.bottom) this.bottom = bottom;
    }

    public void printRV() {
        if (! name.equals("__program_begin__")) System.out.println(name + ":");
        blocks.forEach(x -> x.printRV());
    }
}
