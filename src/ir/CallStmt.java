package ir;

import java.util.ArrayList;

public class CallStmt extends YyStmt {
    private Function function;
    private ArrayList<Item> parameters;
    private Local result;

    public CallStmt(Function function, ArrayList<Item> parameters, Local result) {
        this.function = function;
        this.parameters = parameters;
        this.result = result;
    }

    @Override
    public String toString() {
        StringBuilder ret = new StringBuilder(result + " = " + function + "(");
        boolean flag = false;
        for (Item parameter : parameters) {
            if (flag) ret.append(" ,");
            ret.append(parameter);
            flag = true;
        }
        ret.append(")");
        return ret.toString();
    }
}
