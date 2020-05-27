package ir;

import ir.items.Item;

import java.io.PrintWriter;
import java.util.ArrayList;

public class DeclaredFunction extends Function {
    private ArrayList<Item> args = new ArrayList<>();

    public DeclaredFunction(String name) {
        super(name);
    }

    public void defineArg(Item reg){
        args.add(reg);
    }

    public ArrayList<Item> getArgs() {
        return args;
    }

    @Override
    public void printIR(PrintWriter writer) {
        StringBuilder str = new StringBuilder("fun " + name + "(");
        boolean flag = false;
        for (Item parameter : args) {
            if (flag) str.append(" ,");
            str.append(parameter);
            flag = true;
        }
        str.append(")");
        writer.println(str);

        for (Block block : super.blockList) {
            block.printIR(writer);
        }
    }
}
