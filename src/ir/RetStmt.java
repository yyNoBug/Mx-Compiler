package ir;

public class RetStmt extends TerminalStmt {
    private Register register;

    public RetStmt(Register register) {
        this.register = register;
    }

    @Override
    public String toString() {
        return "return " + register;
    }
}
