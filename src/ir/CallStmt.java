package ir;

import java.util.ArrayList;

public class CallStmt extends YyStmt {
    private Function function;
    private ArrayList<Register> parameters;
    private Local result;

    public CallStmt(Function function, ArrayList<Register> parameters, Local result) {
        this.function = function;
        this.parameters = parameters;
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(result + " = " + function + "(");
        boolean flag = false;
        for (Register parameter : parameters) {
            if (flag) ret.append(" ,");
            ret.append(parameter);
            flag = true;
        }
        ret.append(")");
        return ret.toString();
    }
}
