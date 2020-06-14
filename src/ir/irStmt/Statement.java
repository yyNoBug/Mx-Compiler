package ir.irStmt;


import ir.Block;
import ir.IRVisitor;
import ir.items.Item;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

abstract public class Statement {
    abstract public void accept(IRVisitor visitor);

    abstract public HashSet<Item> getUses();

    public abstract Statement transform(HashMap<Item, Item> itemMap);

    public void translateTarget(Map<Block, Block>  map){

    }
}
