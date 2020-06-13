import ast.ProgramNode;
import backend.RISCVGenerator;
import backend.RegisterAllocator;
import frontend.*;
import optimize.Mem2Reg;
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

        InputStream in;
        if (args.length > 0) {
            in = new FileInputStream("test.txt");
        } else {
            in = new FileInputStream("data.txt");
        }

        ProgramNode programNode = buildASTTree(in);

        TopLevelScope globalScope = new TopLevelScope();
        new ClassDeclScanner(globalScope).visit(programNode);
        new BuiltinFunctionInitializer(globalScope).initialize();
        new GlobalFuncDeclScanner(globalScope).visit(programNode);
        new ClassMemberScanner(globalScope).visit(programNode);
        new EntityBuilder(globalScope).visit(programNode);
        new SemanticChecker(globalScope).visit(programNode);

        if (args.length > 0 && args[0].equals("sema")) return;

        var irBuilder = new IRBuilder(globalScope);
        irBuilder.visit(programNode);
        var irTop = irBuilder.getTop();
        if(args.length == 0) irTop.printIR("ir.out");
        new Mem2Reg(irTop);
        if(args.length == 0) irTop.printIR("ssa.out");

        var rvGenerator = new RISCVGenerator(irTop);
        rvGenerator.generateRVAssembly();
        var rvTop = rvGenerator.getRvTop();


        if (args.length == 0) {
            rvTop.printRV("fakeOutput");
        }


        new RegisterAllocator(rvTop).allocate();
        //new AddrValidator(rvTop).validate();
        if (args.length == 0) {
            rvTop.printRV("debug");
        } else {
            rvTop.printRV(args[0]);
        }
    }

}
