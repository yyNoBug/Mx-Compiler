package ir;

import ir.items.Global;
import ir.items.StringConst;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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

    public void printIR(String filename){
        try {
            FileWriter fileWriter = new FileWriter(filename);
            PrintWriter writer = new PrintWriter(fileWriter);

            for (Global global : globals) {
                writer.println("global " + global);
            }

            for (StringConst str : strs) {
                writer.println("string " + str + " = \"" + str.getEscapedString() + "\"");
            }

            writer.println();

            for (DeclaredFunction function : functions) {
                function.printIR(writer);
                writer.println();
            }

            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }
}
