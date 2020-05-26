package ir;

import ir.items.Global;
import ir.items.StringConst;

import java.util.ArrayList;
import java.util.List;

public class IRTop {
    private List<Global> globals = new ArrayList<>();
    private List<StringConst> strs = new ArrayList<>();
    private List<DeclaredFunction> functions = new ArrayList<>();

    public List<Global> getGlobals() {
        return globals;
    }

    public List<StringConst> getStrs() {
        return strs;
    }

    public List<DeclaredFunction> getFunctions() {
        return functions;
    }

    public void add(Global global) {
        globals.add(global);
    }

    public void add(StringConst str) {
        strs.add(str);
    }

    public void add(DeclaredFunction function) {
        functions.add(function);
    }
}
