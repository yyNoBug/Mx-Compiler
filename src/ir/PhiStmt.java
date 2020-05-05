package ir;

import java.util.Map;

public class PhiStmt extends Statement {
    private Local target;
    private Map<Block, Register> map;

    public PhiStmt(Local target, Map<Block, Register> map) {
        this.target = target;
        this.map = map;
    }
}
