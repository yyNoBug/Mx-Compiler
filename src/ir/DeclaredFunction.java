package ir;

import java.util.ArrayList;

public class DeclaredFunction extends Function {
    private ArrayList<Register> args = new ArrayList<>();

    public DeclaredFunction(String name) {
        super(name);
    }

    public void defineArg(Register reg){
        args.add(reg);
    }

    public ArrayList<Register> getArgs() {
        return args;
    }

    @Override
    public void printIR() {
        StringBuilder str = new StringBuilder("fun " + name + "(");
        boolean flag = false;
        for (Register parameter : args) {
            if (flag) str.append(" ,");
            str.append(parameter);
            flag = true;
        }
        str.append(")");
        System.out.println(str);

        for (Block block : super.blockList) {
            block.printIR();
        }
    }
}
