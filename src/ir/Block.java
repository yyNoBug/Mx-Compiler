package ir;

import java.util.ArrayList;

public class Block {
    private ArrayList<Statement> stmtList;
    private String name;

    public Block(String name) {
        this.name = name;
    }

    public void add (Statement stmt) {
        stmtList.add(stmt);
    }

    public Statement peak() {
        return stmtList.get(stmtList.size() - 1);
    }
}
