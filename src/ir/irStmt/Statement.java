package ir.irStmt;


import ir.IRVisitor;
import ir.items.Item;

import java.util.HashSet;

abstract public class Statement {
    abstract public void accept(IRVisitor visitor);

    abstract public HashSet<Item> getUses();
}
