package ir;

import java.util.Map;

public class PhiStmt extends Statement {
    private Local target;
    private Map<Block, Register> map;

    public PhiStmt(Local target, Map<Block, Register> map) {
        this.target = target;
        this.map = map;
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
}
