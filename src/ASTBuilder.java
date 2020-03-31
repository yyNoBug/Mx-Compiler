import ast.ASTNode;
import parser.MxLangBaseVisitor;
import parser.MxLangParser;

public class ASTBuilder extends MxLangBaseVisitor<ASTNode> {


    @Override
    public ASTNode visitProgram(MxLangParser.ProgramContext ctx) {


        return super.visitProgram(ctx);
    }
}
