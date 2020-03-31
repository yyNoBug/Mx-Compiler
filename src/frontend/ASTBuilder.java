package frontend;

import ast.*;
import parser.MxLangBaseVisitor;
import parser.MxLangParser;

import java.util.ArrayList;
import java.util.List;

public class ASTBuilder extends MxLangBaseVisitor<ASTNode> {
    ASTErrorRecorder recorder;

    public ASTBuilder(ASTErrorRecorder recorder) {
        this.recorder = recorder;
    }

    @Override
    public ASTNode visitProgram(MxLangParser.ProgramContext ctx) {
        List<DeclarationNode> declarations = new ArrayList<>();
        if (ctx.section() != null) {
            for (MxLangParser.SectionContext sectionContext : ctx.section()) {
                ASTNode section = visit(sectionContext);
                if (section instanceof VarDeclListNode) declarations.addAll(((VarDeclListNode) section).getVariables());
                declarations.add(((DeclarationNode) section));
            }
        }
        return new ProgramNode(new Location(ctx.getStart()), declarations);
    }

    @Override
    public ASTNode visitSection(MxLangParser.SectionContext ctx) {
        if (ctx.functionDefinition() != null) return visit(ctx.functionDefinition());
        if (ctx.classDefinition() != null) return visit(ctx.classDefinition());
        if (ctx.variableDefinition() != null) return visit(ctx.variableDefinition());
        throw new CompilerException(new Location(ctx), "Invalid Section.");
    }

    @Override
    public ASTNode visitClassDefinition(MxLangParser.ClassDefinitionContext ctx) {
        String id = ctx.ID().getText();
        List<VarDeclSingleNode> memberVars = new ArrayList<>();
        List<FunDeclNode> memberFuns = new ArrayList<>();
        ClassConstructorNode constructor;
        if (ctx.variableDefinition() != null) {
            for (MxLangParser.VariableDefinitionContext variableDefinitionContext : ctx.variableDefinition()) {
                memberVars.addAll(((VarDeclListNode) visit(variableDefinitionContext)).getVariables());
            }
        }
        if (ctx.functionDefinition() != null) {
            for (MxLangParser.FunctionDefinitionContext functionDefinitionContext : ctx.functionDefinition()) {
                memberFuns.add((FunDeclNode) visit(functionDefinitionContext));
            }
        }
        if (ctx.classConstructor().size()  > 1)
            recorder.record(new Location(ctx), "More than one class constructor.");
        if (ctx.classConstructor() == null) constructor = null;
        else constructor = (ClassConstructorNode) visit(ctx.classConstructor().get(0));
        return new ClassDeclNode(new Location(ctx), id, memberVars, memberFuns, constructor);
    }

    @Override
    public ASTNode visitFunctionDefinition(MxLangParser.FunctionDefinitionContext ctx) {
        TypeNode returnType;
        if (ctx.type() != null) returnType = (TypeNode) visit(ctx.type());
        else throw new CompilerException(new Location(ctx), "Parser Failed.");
        String name = ctx.ID().getText();
        List<VarDeclSingleNode> parameterList = new ArrayList<>();
        if (ctx.functionParameterList() != null) {
            for (MxLangParser.ParameterDeclarationContext parameterDeclarationContext :
                    ctx.functionParameterList().parameterDeclaration()) {
                ASTNode paraDeclNode = visit(parameterDeclarationContext);
                parameterList.add((VarDeclSingleNode) paraDeclNode);
            }
        }
        StatementBlockNode body = (StatementBlockNode) visit(ctx.statementBlock());
        return new FunDeclNode(new Location(ctx), returnType, name, parameterList, body);
    }

    @Override
    public ASTNode visitVariableDefinition(MxLangParser.VariableDefinitionContext ctx) {
        return visitVardeclStatement(ctx.vardeclStatement());
    }

    @Override
    public ASTNode visitFunctionParameterList(MxLangParser.FunctionParameterListContext ctx) {
        throw new CompilerException(new Location(ctx), "Invalid visit.");
    }

    @Override
    public ASTNode visitParameterDeclaration(MxLangParser.ParameterDeclarationContext ctx) {
        TypeNode type = (TypeNode) visit(ctx.type());
        String id = ctx.ID().getText();
        return new VarDeclSingleNode(new Location(ctx), type, id, null);
    }

    @Override
    public ASTNode visitType(MxLangParser.TypeContext ctx) {
        if (ctx.arrayType() != null) return visit(ctx.arrayType());
        if (ctx.simpleType() != null) return visit(ctx.simpleType());
        throw new CompilerException(new Location(ctx), "Parser Failed.");
    }

    @Override
    public ASTNode visitSimpleType(MxLangParser.SimpleTypeContext ctx) {
        String type = ctx.getText();
        return new SimpleTypeNode(new Location(ctx), type);
    }

    @Override
    public ASTNode visitArrayType(MxLangParser.ArrayTypeContext ctx) {
        String type = ctx.simpleType().getText();
        int dimension = ctx.squareBracket().size();
        return new ArrayTypeNode(new Location(ctx), type, dimension);
    }

    @Override
    public ASTNode visitConstant(MxLangParser.ConstantContext ctx) {
        if (ctx.NUM() != null) return new NumConstNode(new Location(ctx), ctx.getText());
        if (ctx.StringConstant() != null)
            return new StringConstNode(new Location(ctx), unescape(ctx.getText(), new Location(ctx)));
        if (ctx.NULL() != null) return new NullConstNode(new Location(ctx));
        if (ctx.TRUE() != null) return new BoolConstNode(new Location(ctx), true);
        if (ctx.FALSE() != null) return new BoolConstNode(new Location(ctx), false);
        throw new CompilerException(new Location(ctx), "Parser failed.");
    }

    private String unescape(String str, Location loc) {
        StringBuilder strBuilder = new StringBuilder();
        for (int i = 0; i < str.length(); ++i) {
            if (i + 1 < str.length() && str.charAt(i) == '\\') {
                switch (str.charAt(i + 1)) {
                    case '\\': strBuilder.append('\\'); break;
                    case '\"': strBuilder.append('\"'); break;
                    case 'n': strBuilder.append('\n'); break;
                    case 't': strBuilder.append('\t'); break;
                    default: recorder.record(loc, "Failed to unescape.");
                }
                ++i;
            } else {
                strBuilder.append(str.charAt(i));
            }
        }
        return strBuilder.toString();
    }

    @Override
    public ASTNode visitNewExpr(MxLangParser.NewExprContext ctx) {
        TypeNode type = ((TypeNode) visit(ctx.simpleType()));
        List<ExprNode> dims = new ArrayList<>();
        int numDims;
        if (ctx.squareBracketWithExpression() != null) {
            numDims = ctx.squareBracketWithExpression().size();
            for (MxLangParser.SquareBracketWithExpressionContext squareBracketWithExpressionContext
                    : ctx.squareBracketWithExpression()) {
                if (squareBracketWithExpressionContext.expression() != null) {
                    dims.add(((ExprNode) visit(squareBracketWithExpressionContext.expression())));
                }
            }
        }
        else numDims = 0;
        return new NewExprNode(new Location(ctx), type, dims, numDims);
    }

    @Override
    public ASTNode visitParExpr(MxLangParser.ParExprContext ctx) {
        return visit(ctx.expression());
    }

    @Override
    public ASTNode visitPrefixExpr(MxLangParser.PrefixExprContext ctx) {
        PrefixExprNode.Op op;
        switch (ctx.op.getText()) {
            case "++": op = PrefixExprNode.Op.PREFIX_INC; break;
            case "--": op = PrefixExprNode.Op.PREFIX_DEC; break;
            case "+" : op = PrefixExprNode.Op.POS; break;
            case "-" : op = PrefixExprNode.Op.NEG; break;
            case "!" : op = PrefixExprNode.Op.LOGIC_NOT; break;
            case "~" : op = PrefixExprNode.Op.BITWISE_NOT; break;
            default:
                throw new CompilerException(new Location(ctx), "Parser Failed.");
        }
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new PrefixExprNode(new Location(ctx), op, expr);
    }

    @Override
    public ASTNode visitThisExpr(MxLangParser.ThisExprContext ctx) {
        return new ThisExprNode(new Location(ctx));
    }

    @Override
    public ASTNode visitFunCallExpr(MxLangParser.FunCallExprContext ctx) {
        ExprNode name = ((ExprNode) visit(ctx.expression().get(0)));
        List<ExprNode> parameterList = new ArrayList<>();
        for (int i = 1; i < ctx.expression().size(); i++) {
            ExprNode expr;
            expr = (ExprNode) visit(ctx.expression().get(i));
            parameterList.add(expr);
        }
        return new FunCallExprNode(new Location(ctx), name, parameterList);
    }

    @Override
    public ASTNode visitArrayExpr(MxLangParser.ArrayExprContext ctx) {
        ExprNode array, index;
        if (ctx.expression().size() != 2) throw new CompilerException(new Location(ctx), "Parser Failed.");
        array = (ExprNode) visit(ctx.expression().get(0));
        index = (ExprNode) visit(ctx.expression().get(1));
        return new ArrayExprNode(new Location(ctx), array, index);
    }

    @Override
    public ASTNode visitSuffixExpr(MxLangParser.SuffixExprContext ctx) {
        SuffixExprNode.Op op;
        switch (ctx.op.getText()) {
            case "++": op = SuffixExprNode.Op.SUFFIX_INC; break;
            case "--": op = SuffixExprNode.Op.SUFFIX_DEC; break;
            default:
                throw new CompilerException(new Location(ctx), "Parser Failed.");
        }
        ExprNode expr = (ExprNode) visit(ctx.expression());
        return new SuffixExprNode(new Location(ctx), op, expr);
    }

    @Override
    public ASTNode visitMemberExpr(MxLangParser.MemberExprContext ctx) {
        ExprNode expr = (ExprNode) visit(ctx.expression());
        String member = ctx.ID().getText();
        return new MemberExprNode(new Location(ctx), expr, member);
    }

    @Override
    public ASTNode visitBinaryExpr(MxLangParser.BinaryExprContext ctx) {
        BinaryExprNode.Op op;
        switch (ctx.op.getText()) {
            case "*" : op = BinaryExprNode.Op.MUL; break;
            case "/" : op = BinaryExprNode.Op.DIV; break;
            case "%" : op = BinaryExprNode.Op.MOD; break;
            case "+" : op = BinaryExprNode.Op.ADD; break;
            case "-" : op = BinaryExprNode.Op.SUB; break;
            case "<<": op = BinaryExprNode.Op.SLA; break;
            case ">>": op = BinaryExprNode.Op.SRA; break;
            case "<" : op = BinaryExprNode.Op.LTH; break;
            case ">" : op = BinaryExprNode.Op.GTH; break;
            case "<=": op = BinaryExprNode.Op.LEQ; break;
            case ">=": op = BinaryExprNode.Op.GEQ; break;
            case "==": op = BinaryExprNode.Op.EQ; break;
            case "!=": op = BinaryExprNode.Op.NEQ; break;
            case "&": op = BinaryExprNode.Op.BITWISE_AND; break;
            case "^": op = BinaryExprNode.Op.BITWISE_XOR; break;
            case "|": op = BinaryExprNode.Op.BITWISE_OR; break;
            case "&&": op = BinaryExprNode.Op.LOGIC_AND; break;
            case "||": op = BinaryExprNode.Op.LOGIC_OR; break;
            default: throw new CompilerException(new Location(ctx), "Parser Failed.");
        }
        ExprNode lhs, rhs;
        if (ctx.expression().size() != 2) throw new CompilerException(new Location(ctx), "Parser Failed.");
        lhs = (ExprNode) visit(ctx.expression().get(0));
        rhs = (ExprNode) visit(ctx.expression().get(1));
        return new BinaryExprNode(new Location(ctx), op, lhs, rhs);
    }

    @Override
    public ASTNode visitAssignExpr(MxLangParser.AssignExprContext ctx) {
        ExprNode lhs, rhs;
        if (ctx.expression().size() != 2) throw new CompilerException(new Location(ctx), "Parser Failed.");
        lhs = (ExprNode) visit(ctx.expression().get(0));
        rhs = (ExprNode) visit(ctx.expression().get(1));
        return new AssignExprNode(new Location(ctx), lhs, rhs);
    }

    @Override
    public ASTNode visitIdExpr(MxLangParser.IdExprContext ctx) {
        String id = ctx.ID().getText();
        return new IdExprNode(new Location(ctx), id);
    }

    @Override
    public ASTNode visitConstExpr(MxLangParser.ConstExprContext ctx) {
        return visit(ctx.constant());
    }

    @Override
    public ASTNode visitVardeclStatement(MxLangParser.VardeclStatementContext ctx) {
        TypeNode type = ((TypeNode) visit(ctx.type()));
        List<VarDeclSingleNode> variables = new ArrayList<>();
        for (MxLangParser.ExpressionContext expressionContext : ctx.expression()) {
            variables.add((VarDeclSingleNode) visit(expressionContext));
        }
        return new VarDeclListNode(new Location(ctx), type, variables);
    }

    @Override
    public ASTNode visitIfStatement(MxLangParser.IfStatementContext ctx) {
        ExprNode condition = (ExprNode) visit(ctx.expression());
        StatementNode thenStatement, elseStatement = null;
        if (ctx.statement().size() >= 2) throw new CompilerException(new Location(ctx), "Parser failed.");
        if (ctx.thenStatement == null) throw new CompilerException(new Location(ctx), "Parser failed.");
        thenStatement = (StatementNode) visit(ctx.thenStatement);
        if (ctx.elseStatement != null) elseStatement = (StatementNode) visit(ctx.elseStatement);

        return new IfStatementNode(new Location(ctx), condition, thenStatement, elseStatement);
    }

    @Override
    public ASTNode visitLoopStatement(MxLangParser.LoopStatementContext ctx) {
        if (ctx.forStatement() != null) {
            return visitForStatement(ctx.forStatement());
        }
        if (ctx.whileStatement() != null) {
            return visitWhileStatement(ctx.whileStatement());
        }
        throw new CompilerException(new Location(ctx), "Parser failed.");
    }

    @Override
    public ASTNode visitJmpStatement(MxLangParser.JmpStatementContext ctx) {
        if (ctx.breakStatement() != null) {
            return visitBreakStatement(ctx.breakStatement());
        }
        if (ctx.continueStatement() != null) {
            return visitContinueStatement(ctx.continueStatement());
        }
        if (ctx.returnStatement() != null) {
            return visitReturnStatement(ctx.returnStatement());
        }
        throw new CompilerException(new Location(ctx), "Parser failed.");
    }

    @Override
    public ASTNode visitExprStatement(MxLangParser.ExprStatementContext ctx) {
        ExprNode expression = ((ExprNode) visit(ctx.expression()));
        return new ExprStatementNode(new Location(ctx), expression);
    }

    @Override
    public ASTNode visitBreakStatement(MxLangParser.BreakStatementContext ctx) {
        return new BreakStatementNode(new Location(ctx));
    }

    @Override
    public ASTNode visitContinueStatement(MxLangParser.ContinueStatementContext ctx) {
        return new ContinueStatementBlock(new Location(ctx));
    }

    @Override
    public ASTNode visitReturnStatement(MxLangParser.ReturnStatementContext ctx) {
        ExprNode expr;
        if (ctx.expression() != null) expr = (ExprNode) visit(ctx.expression());
        else expr = null;
        return new ReturnStatementBlock(new Location(ctx), expr);
    }

    @Override
    public ASTNode visitStatementBlock(MxLangParser.StatementBlockContext ctx) {
        List<StatementNode> statements = new ArrayList<>();
        for (MxLangParser.StatementContext statementContext : ctx.statement()) {
            StatementNode statement = (StatementNode) visit(statementContext);
            if (statement != null) statements.add(statement);
        }
        return new StatementBlockNode(new Location(ctx), statements);
    }

    @Override
    public ASTNode visitWhileStatement(MxLangParser.WhileStatementContext ctx) {
        ExprNode condition = (ExprNode) visit(ctx.expression());
        StatementNode body = (StatementNode) visit(ctx.statement());
        return new WhileStatementNode(new Location(ctx), condition, body);
    }

    @Override
    public ASTNode visitForStatement(MxLangParser.ForStatementContext ctx) {
        ExprNode init, condition, step;
        if (ctx.init != null) init = (ExprNode) visit(ctx.init);
        else init = null;
        if (ctx.condition != null) condition = (ExprNode) visit(ctx.condition);
        else condition = null;
        if (ctx.step != null) step = (ExprNode) visit(ctx.step);
        else step = null;
        StatementNode body = (StatementNode) visit(ctx.statement());
        return new ForStatementNode(new Location(ctx), init, condition, step, body);
    }

    @Override
    public ASTNode visitClassConstructor(MxLangParser.ClassConstructorContext ctx) {
        TypeNode type = new SimpleTypeNode(new Location(ctx), "void");
        String name = ctx.ID().getText();
        List<VarDeclSingleNode> parameterList = new ArrayList<>();
        StatementBlockNode body = (StatementBlockNode) visit(ctx.statementBlock());
        return new ClassConstructorNode(new Location(ctx), type, name, parameterList, body);
    }

    @Override
    public ASTNode visitBlankStatement(MxLangParser.BlankStatementContext ctx) {
        return new BlankStatementNode(new Location(ctx));
    }

    @Override
    public ASTNode visitStatement(MxLangParser.StatementContext ctx) {
        if (ctx.exprStatement() != null) return visit(ctx.exprStatement());
        if (ctx.vardeclStatement() != null) return visit(ctx.ifStatement());
        if (ctx.loopStatement() != null) return visit(ctx.loopStatement());
        if (ctx.jmpStatement() != null) return  visit(ctx.jmpStatement());
        if (ctx.blankStatement() != null) return visit(ctx.blankStatement());
        if (ctx.statementBlock() != null) return visit(ctx.statementBlock());
        throw new CompilerException(new Location(ctx), "Invalid Section.");
    }
}
