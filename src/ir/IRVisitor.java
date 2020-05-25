package ir;

import ir.irStmt.*;

public interface IRVisitor {
    // void visit(TerminalStmt stmt);
    // void visit(YyStmt stmt);
    void visit(AllocateStmt stmt);
    void visit(BranchStmt stmt);
    void visit(CallStmt stmt);
    void visit(JmpStmt stmt);
    void visit(LoadStmt stmt);
    void visit(OpStmt stmt);
    void visit(PhiStmt stmt);
    void visit(RetStmt stmt);
    void visit(StoreStmt stmt);

    default void visit(Statement stmt) {
        stmt.accept(this);
    }
}
