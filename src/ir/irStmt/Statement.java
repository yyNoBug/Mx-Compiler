package ir.irStmt;


import ir.IRVisitor;

abstract public class Statement {
    abstract public void accept(IRVisitor visitor);
}
