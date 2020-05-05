package ir;

import java.util.ArrayList;

public class Block {
    private ArrayList<Statement> stmtList = new ArrayList<>();
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

    @Override
    public String toString() {
        return name;
    }

    public void printIR() {
        System.out.println("  " + name + ":");
        for (Statement statement : stmtList) {
            System.out.println("    " + statement);
        }
    }
}
