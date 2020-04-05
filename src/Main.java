import ast.ProgramNode;
import frontend.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import parser.MxLangErrorListener;
import parser.MxLangLexer;
import parser.MxLangParser;
import scope.TopLevelScope;

import java.io.FileInputStream;
import java.io.InputStream;

public class Main {

    private static ProgramNode buildASTTree(InputStream in) throws Exception {
        MxLangLexer lexer = new MxLangLexer(CharStreams.fromStream(in));
        lexer.addErrorListener(new MxLangErrorListener());
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MxLangParser parser = new MxLangParser(tokens);
        parser.addErrorListener(new MxLangErrorListener());
        ParseTree tree = parser.program();
        ASTBuilder astBuilder = new ASTBuilder(new ASTErrorListener());
        return (ProgramNode) astBuilder.visit(tree);
    }

    public static void main (String[] args) throws Exception {


        InputStream in = new FileInputStream("test.txt");

        ProgramNode programNode = buildASTTree(in);

        TopLevelScope globalScope = new TopLevelScope();
        new ClassDeclScanner(globalScope).visit(programNode);
        new BuiltinFunctionInitializer(globalScope).initialize();
        new GlobalFuncDeclScanner(globalScope).visit(programNode);
        new ClassMemberScanner(globalScope).visit(programNode);
        new EntityBuilder(globalScope).visit(programNode);
        new SemanticChecker(globalScope).visit(programNode);

        /*
        try {
            ProgramNode programNode = buildASTTree(in);

            TopLevelScope globalScope = new TopLevelScope();
            new ClassDeclScanner(globalScope).visit(programNode);
            new BuiltinFunctionInitializer(globalScope).initialize();
            new GlobalFuncDeclScanner(globalScope).visit(programNode);
            new GlobalFuncDeclScanner(globalScope).visit(programNode);
            new ClassMemberScanner(globalScope).visit(programNode);
            new EntityBuilder(globalScope).visit(programNode);
            new SemanticChecker(globalScope).visit(programNode);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        */
    }


}
