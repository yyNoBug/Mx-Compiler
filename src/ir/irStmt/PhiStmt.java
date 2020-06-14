package ir.irStmt;

import ir.Block;
import ir.IRVisitor;
import ir.items.Item;
import ir.items.Local;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class PhiStmt extends Statement {
    private Local result;
    private Map<Block, Item> map;

    public PhiStmt(Local result, Map<Block, Item> map) {
        this.result = result;
        this.map = map;
    }

    public Local getResult() {
        return result;
    }

    public Map<Block, Item> getMap() {
        return map;
    }

    @Override
    public Statement transform(HashMap<Item, Item> itemMap) {
        var newMap = new HashMap<Block, Item>();
        map.forEach((x, y) -> {
            newMap.put(x, itemMap.getOrDefault(y, y));
        });
        var newResult = result;
        if (itemMap.containsKey(result)) newResult = (Local) itemMap.get(result);
        return new PhiStmt(newResult, newMap);
    }

    @Override
    public void translateTarget(Map<Block, Block> blkMap) {
        var newMap = new HashMap<Block, Item>();
        map.forEach((x, y) -> {
            if (blkMap.containsKey(x)) {
                newMap.put(blkMap.get(x), y);
            } else {
                newMap.put(x, y);
            }
        });
        map = newMap;
    }

    @Override
    public Item getDef() {
        return result;
    }

    @Override
    public HashSet<Item> getUses() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(result + " = phi");
        for (var i : map.entrySet()){
            ret.append(" (");
            ret.append(i.getKey());
            ret.append(" -> ");
            ret.append(i.getValue());
            ret.append(")");
        }
        return ret.toString();
    }

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
