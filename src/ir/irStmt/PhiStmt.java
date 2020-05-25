package ir.irStmt;

import ir.Block;
import ir.IRVisitor;
import ir.items.Item;
import ir.items.Local;

import java.util.Map;

public class PhiStmt extends Statement {
    private Local target;
    private Map<Block, Item> map;

    public PhiStmt(Local target, Map<Block, Item> map) {
        this.target = target;
        this.map = map;
    }

    public Local getTarget() {
        return target;
    }

    public Map<Block, Item> getMap() {
        return map;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(target + " = phi");
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
