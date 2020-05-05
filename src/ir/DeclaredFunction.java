package ir;

import java.util.ArrayList;

public class DeclaredFunction extends Function {
    private ArrayList<Register> args = new ArrayList<>();

    public void defineArg(Register reg){
        args.add(reg);
    }
}
