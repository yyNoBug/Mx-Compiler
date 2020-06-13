package ir.irStmt;

import ir.Function;
import ir.IRVisitor;
import ir.items.Item;
import ir.items.Local;

import java.util.ArrayList;
import java.util.HashSet;

public class CallStmt extends YyStmt {
    private Function function;
    private ArrayList<Item> parameters;
    private Local result;

    public CallStmt(Function function, ArrayList<Item> parameters, Local result) {
        this.function = function;
        this.parameters = parameters;
        this.result = result;
    }

    public String getSymbol() {
        return function.getName();
    }

    public Local getResult() {
        return result;
    }

    public ArrayList<Item> getParameters() {
        return parameters;
    }

    @Override
    public HashSet<Item> getUses() {
        return new HashSet<>() {{
            this.addAll(parameters);
        }};
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

    public void accept(IRVisitor visitor) {
        visitor.visit(this);
    }
}
