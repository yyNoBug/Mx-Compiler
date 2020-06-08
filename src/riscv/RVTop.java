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

    public void printRV(String mode) {
        try {
            PrintWriter writer;
            switch (mode) {
                case "yynb":
                    writer = new PrintWriter(System.out);
                    break;
                case "debug": {
                    FileWriter fileWriter = new FileWriter("builtin/output.s");
                    writer = new PrintWriter(fileWriter);
                    break;
                }
                case "fakeOutput": {
                    FileWriter fileWriter = new FileWriter("builtin/" + mode + ".s");
                    writer = new PrintWriter(fileWriter);
                    break;
                }
                case "codegen": {
                    FileWriter fileWriter = new FileWriter("output.s");
                    writer = new PrintWriter(fileWriter);
                    break;
                }
                default:
                    writer = new PrintWriter(System.out);
                    break;
            }
            functions.forEach(x -> x.printRV(writer));
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
