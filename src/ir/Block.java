package ir;

import ir.irStmt.PhiStmt;
import ir.irStmt.Statement;

import java.io.PrintWriter;
import java.util.ArrayList;

public class Block {
    private ArrayList<Statement> stmtList = new ArrayList<>();
    private ArrayList<PhiStmt> phiStmts = new ArrayList<>();
    private String name;

    public Block(String name) {
        this.name = name;
    }

    public ArrayList<Statement> getStmtList() {
        return stmtList;
    }

    public ArrayList<PhiStmt> getPhiStmts() {
        return phiStmts;
    }

    public String getName() {
        return name;
    }

    public void add (Statement stmt) {
        stmtList.add(stmt);
        if (stmt instanceof PhiStmt) phiStmts.add(((PhiStmt) stmt));
    }

    public Statement peak() {
        if (stmtList.size() == 0) return null;
        return stmtList.get(stmtList.size() - 1);
    }

    @Override
    public String toString() {
        return name;
    }

    public void printIR(PrintWriter writer) {
        writer.println("  " + name + ":");
        for (Statement statement : stmtList) {
            writer.println("    " + statement);
        }
    }
}
