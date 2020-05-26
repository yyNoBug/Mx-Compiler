package riscv;

import java.util.ArrayList;

public class RVTop {
    private ArrayList<RVFunction> functions = new ArrayList<>();

    public void add(RVFunction function) {
        functions.add(function);
    }

    public ArrayList<RVFunction> getFunctions() {
        return functions;
    }

    public void printRV() {
        functions.forEach(x -> x.printRV());
    }
}
