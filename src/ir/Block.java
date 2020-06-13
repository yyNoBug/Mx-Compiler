package ir;

import ir.irStmt.BranchStmt;
import ir.irStmt.JmpStmt;
import ir.irStmt.PhiStmt;
import ir.irStmt.Statement;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Block {
    private List<Statement> stmtList = new ArrayList<>();
    private List<PhiStmt> phiStmts = new ArrayList<>();
    private String name;
    public Set<Block> precursors = new HashSet<>();
    public Set<Block> successors = new HashSet<>();

    public Block(String name) {
        this.name = name;
    }

    public List<Statement> getStmtList() {
        return stmtList;
    }

    public List<PhiStmt> getPhiStmts() {
        return phiStmts;
    }

    public String getName() {
        return name;
    }

    public void add (Statement stmt) {
        if (stmt instanceof PhiStmt) {
            var itr = stmtList.listIterator();
            for (PhiStmt phiStmt : phiStmts) {
                itr.next();
            }
            itr.add(stmt);
            phiStmts.add(((PhiStmt) stmt));
            return;
        }
        stmtList.add(stmt);
        if (stmt instanceof JmpStmt) {
            var dest = ((JmpStmt) stmt).getDestination();
            this.successors.add(dest);
            dest.precursors.add(this);
        }
        if (stmt instanceof BranchStmt) {
            var elseBlk = ((BranchStmt) stmt).getElseBlock();
            var thenBlk = ((BranchStmt) stmt).getThenBlock();
            this.successors.add(elseBlk);
            elseBlk.precursors.add(this);
            this.successors.add(thenBlk);
            thenBlk.precursors.add(this);
        }
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
