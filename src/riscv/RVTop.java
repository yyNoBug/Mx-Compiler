package riscv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class RVTop {
    private ArrayList<RVFunction> functions = new ArrayList<>();

    public void add(RVFunction function) {
        functions.add(function);
    }

    public ArrayList<RVFunction> getFunctions() {
        return functions;
    }

    public void printRV(int mode) {
        try {
            PrintWriter writer;
            if (mode == 1) {
                writer = new PrintWriter(System.out);
            } else {
                FileWriter fileWriter = new FileWriter("builtin/output.s");
                writer = new PrintWriter(fileWriter);
            }
            functions.forEach(x -> x.printRV(writer));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
