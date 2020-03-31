import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.MxLangLexer;
import parser.MxLangParser;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    public static void main (String[] args) throws Exception {
        InputStream in = new FileInputStream("test.txt");
        System.out.println("Lexer Test:");
        MxLangLexer lexer = new MxLangLexer(CharStreams.fromStream(in));
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MxLangParser parser = new MxLangParser(tokens);
        ParseTree tree = parser.program();
        System.out.println(tree.toStringTree(parser));
    }

}
