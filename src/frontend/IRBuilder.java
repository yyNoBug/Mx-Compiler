package frontend;

import ast.*;
import ir.*;
import scope.DefinedVariable;
import scope.TopLevelScope;
import type.ClassType;

import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    private ArrayList<DeclaredFunction> declaredFunctions = new ArrayList<>();

    private DeclaredFunction curFunction;
    private Block curBlock;
    private Register curReg;
    private String curClassName;
    private Stack<Boolean> leftValueRequireStack = new Stack<>();
    private boolean isVisitingGlobalVariable;
    private boolean isVisitingParameter;

    private IdMap idMap = new IdMap();
    private GlobalMap globalMap = new GlobalMap();
    private LoopMap loopMap = new LoopMap();
    private FunctionMap functionMap = new FunctionMap();

    private void enterBlock (Block block) {
        curFunction.add(block);
        curBlock = block;
    }

    public IRBuilder(TopLevelScope globalScope) {
        BuiltinFunction.setFunctionMap(functionMap, globalScope);
    }

    public void printIR(){
        var map = globalMap.getGlobalList();
        for (DefinedVariable definedVariable : map) {
            System.out.println("global " + idMap.get(definedVariable));
        }
        System.out.println();
        for (DeclaredFunction function : declaredFunctions) {
            function.printIR();
            System.out.println();
        }
    }

    @Override
    public void visit(ProgramNode node) {
        isVisitingGlobalVariable = true;
        for (DeclarationNode declarationNode: node.getSection_list()) {
            declarationNode.accept(this);
        }

        isVisitingGlobalVariable = false;
        for (DeclarationNode declarationNode : node.getSection_list()) {
            if (!(declarationNode instanceof VarDeclSingleNode)) {
                if (declarationNode instanceof ClassDeclNode) curClassName = ((ClassDeclNode) declarationNode).getId();
                else curClassName = null;
                declarationNode.accept(this);
            }
        }
    }

    @Override
    public void visit(VarDeclSingleNode node) {
        if (isVisitingGlobalVariable) {
            Global global = new Global();
            idMap.put(node.getEntity(), global);
            globalMap.add(node.getEntity(), node.getExpr());
        } else {
            Local local = new Local();
            idMap.put(node.getEntity(), local);
            curBlock.add(new AllocateStmt(local));
            if (isVisitingParameter) curFunction.defineArg(local);

            if (node.getExpr() != null) {
                leftValueRequireStack.push(false);
                node.getExpr().accept(this);
                leftValueRequireStack.pop();
                curBlock.add(new StoreStmt(curReg, local));
            } else {
                curBlock.add(new StoreStmt(new NumConst(0), local));
            }
        }
    }

    @Override
    public void visit(FunDeclNode node) {
        if (isVisitingGlobalVariable) {
            String name;
            if (curClassName == null) name = node.getName();
            else name = curClassName + "." + node.getName();
            curFunction = new DeclaredFunction(name);
            declaredFunctions.add(curFunction);
            functionMap.put(node.getEntity(), curFunction);
            return;
        }

        curFunction = ((DeclaredFunction) functionMap.get(node.getEntity()));
        Block entry = new Block("entry");

        if (node.getName().equals("main")) {
            enterBlock(new Block("init_global"));
            for (DefinedVariable definedVariable : globalMap.getGlobalList()) {
                var expr = globalMap.getNodeMap().get(definedVariable);
                if (expr != null) {
                    leftValueRequireStack.push(false);
                    expr.accept(this);
                    leftValueRequireStack.pop();
                    curBlock.add(new StoreStmt(curReg, idMap.get(definedVariable)));
                }
            }
            curBlock.add(new JmpStmt(entry));
        }

        enterBlock(entry);
        isVisitingParameter = true;
        node.getParameterList().forEach(x -> x.accept(this));
        isVisitingParameter = false;
        node.getBody().accept(this);

        if (!(curBlock.peak() instanceof TerminalStmt)) {
            curBlock.add(new RetStmt(new NumConst(0)));
        }
    }

    @Override
    public void visit(ClassDeclNode node) {
        if(node.getConstructor() != null) node.getConstructor().accept(this);
        // node.getMemberVars().forEach(x->x.accept(this));
        node.getMemberFuns().forEach(x->x.accept(this));
    }

    @Override
    public void visit(ClassConstructorNode node) {
        if (isVisitingGlobalVariable) {
            curFunction = new DeclaredFunction(node.getName() + ".__constructor__");
            declaredFunctions.add(curFunction);
            functionMap.put(node.getEntity(), curFunction);
            return;
        }

        curFunction = ((DeclaredFunction) functionMap.get(node.getEntity()));
        enterBlock(new Block("entry"));

        isVisitingParameter = true;
        node.getParameterList().forEach(x -> x.accept(this));
        isVisitingParameter = false;
        node.getBody().accept(this);

        if (!(curBlock.peak() instanceof TerminalStmt)) {
            curBlock.add(new RetStmt(new NumConst(0)));
        }
    }

    @Override
    public void visit(StatementBlockNode node) {
        node.getStmList().forEach(x->x.accept(this));
    }

    @Override
    public void visit(ExprStatementNode node) {
        node.getExpression().accept(this);
    }

    @Override
    public void visit(VarDeclStatementNode node) {
        node.getVariable().accept(this);
    }

    private int ifCount = 0;
    @Override
    public void visit(IfStatementNode node) {
        Block thenBlock = new Block("if." + ifCount + ".then");
        Block elseBlock = new Block("if." + ifCount + ".else");
        Block endBlock = new Block("if." + ifCount + ".end");
        ifCount++;

        leftValueRequireStack.push(false);
        node.getCondition().accept(this);
        leftValueRequireStack.pop();
        if (node.getElseStatement() != null)
            curBlock.add(new BranchStmt(curReg, thenBlock, elseBlock));
        else
            curBlock.add(new BranchStmt(curReg, thenBlock, endBlock));

        enterBlock(thenBlock);
        curBlock.add(new JmpStmt(endBlock));
        if (node.getThenStatement() != null)
            node.getThenStatement().accept(this);

        enterBlock(elseBlock);
        curBlock.add(new JmpStmt(endBlock));
        if (node.getElseStatement() != null)
            node.getElseStatement().accept(this);

        enterBlock(endBlock);
    }

    private int whileCount = 0;
    @Override
    public void visit(WhileStatementNode node) {
        Block conditionBlock = new Block("while." + whileCount + ".condition");
        Block bodyBlock = new Block("while." + whileCount + ".body");
        Block endBlock = new Block("while." + whileCount + ".end");
        whileCount++;
        curBlock.add(new JmpStmt(conditionBlock));

        enterBlock(conditionBlock);
        leftValueRequireStack.push(false);
        node.getCondition().accept(this);
        leftValueRequireStack.pop();
        curBlock.add(new BranchStmt(curReg, bodyBlock, endBlock));

        enterBlock(bodyBlock);
        if (node.getBody() != null) node.getBody().accept(this);
        curBlock.add(new JmpStmt(conditionBlock));

        enterBlock(endBlock);
        loopMap.put(node, conditionBlock, endBlock);
    }

    private int forCount = 0;
    @Override
    public void visit(ForStatementNode node) {
        if (node.getInit() != null) node.getInit().accept(this);
        Block conditionBlock = new Block("for." + forCount + ".condition");
        Block bodyBlock = new Block("for." + forCount + ".body");
        Block stepBlock = new Block("for." + forCount + ".step");
        Block endBlock = new Block("for." + forCount + ".end");
        curBlock.add(new JmpStmt(conditionBlock));

        enterBlock(conditionBlock);
        leftValueRequireStack.push(false);
        if (node.getCond() != null) node.getCond().accept(this);
        leftValueRequireStack.pop();
        curBlock.add(new BranchStmt(curReg, bodyBlock, endBlock));

        enterBlock(bodyBlock);
        if (node.getStatement() != null)node.getStatement().accept(this);
        curBlock.add(new JmpStmt(stepBlock));

        enterBlock(stepBlock);
        if (node.getStep() != null)node.getStep().accept(this);
        curBlock.add(new JmpStmt(conditionBlock));

        enterBlock(endBlock);
        forCount++;
        loopMap.put(node, conditionBlock, endBlock);
    }

    @Override
    public void visit(ContinueStatementNode node) {
        curBlock.add(new JmpStmt(loopMap.getBody(node.getLoopNode())));
    }

    @Override
    public void visit(BreakStatementNode node) {
        curBlock.add(new JmpStmt(loopMap.getEnd(node.getLoopNode())));
    }

    @Override
    public void visit(ReturnStatementNode node) {
        if (node.getExpr() == null) curBlock.add(new RetStmt(null));
        else {
            leftValueRequireStack.push(false);
            node.getExpr().accept(this);
            leftValueRequireStack.pop();
            curBlock.add(new RetStmt(curReg));
        }
    }

    @Override
    public void visit(FunCallExprNode node) {
        Function function = functionMap.get(node.getFuncExpr().getFuncEntity());
        ArrayList<Register> parameters = new ArrayList<>();
        if (node.getFuncExpr().getFuncEntity().isMemberFunction()) {
            leftValueRequireStack.add(false);
            node.getFuncExpr().accept(this);
            leftValueRequireStack.pop();
            parameters.add(curReg);
        }
        node.getParameterList().forEach(x -> {
            leftValueRequireStack.add(false);
            x.accept(this);
            leftValueRequireStack.pop();
            parameters.add(curReg);
        });
        Local result = new Local();
        curBlock.add(new CallStmt(function, parameters, result));
        curReg = result;
    }

    @Override
    public void visit(ArrayExprNode node) {
        leftValueRequireStack.push(false);
        node.getArray().accept(this);
        leftValueRequireStack.pop();
        Register base = curReg;

        leftValueRequireStack.push(false);
        node.getIndex().accept(this);
        leftValueRequireStack.pop();
        Register ind = curReg;
        Local offset = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.MUL, ind, new NumConst(node.getArray().getType().getEntity().getClassSize()), offset));

        Local ret = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.PLUS, base, offset, ret));
        curReg = ret;
    }

    @Override
    public void visit(MemberExprNode node) {
        leftValueRequireStack.add(false);
        node.getExpr().accept(this);
        leftValueRequireStack.pop();
        Register parent = curReg;
        Local ret = new Local();
        if (node.getFuncEntity() != null) {
            curBlock.add(new OpStmt(OpStmt.Op.PLUS, parent, new NumConst(0), ret));
        } else {
            Register offset = new NumConst(node.getExpr().getType().getEntity().calOffset(node.getMember()) * 4);
            curBlock.add(new OpStmt(OpStmt.Op.PLUS, parent, offset, ret));
            curReg = ret;
        }
    }

    @Override
    public void visit(PrefixExprNode node) {
        leftValueRequireStack.push(false);
        node.getExpr().accept(this);
        leftValueRequireStack.pop();
        Register item = curReg;

        Local ret = new Local();
        switch (node.getOp()){
            case POS:
                curBlock.add(new OpStmt(OpStmt.Op.PLUS, new NumConst(0), item, ret));
                break;
            case NEG:
                curBlock.add(new OpStmt(OpStmt.Op.MINUS, new NumConst(0), item, ret));
                break;
            case BITWISE_NOT:
                curBlock.add(new OpStmt(OpStmt.Op.XOR, item, new NumConst(-1), ret));
                break;
            case LOGIC_NOT:
                curBlock.add(new OpStmt(OpStmt.Op.EQ, item, new NumConst(0), ret));
                break;
            case PREFIX_INC:
                curBlock.add(new OpStmt(OpStmt.Op.PLUS, item, new NumConst(1), ret));
                break;
            case PREFIX_DEC:
                curBlock.add(new OpStmt(OpStmt.Op.MINUS, item, new NumConst(1), ret));
                break;
        }
        curReg = ret;
    }

    @Override
    public void visit(SuffixExprNode node) {
        leftValueRequireStack.push(false);
        node.getExpr().accept(this);
        leftValueRequireStack.pop();
        Register item = curReg;

        Local ret = new Local();
        switch (node.getOp()) {
            case SUFFIX_INC:
                curBlock.add(new OpStmt(OpStmt.Op.PLUS, item, new NumConst(1), ret));
                break;
            case SUFFIX_DEC:
                curBlock.add(new OpStmt(OpStmt.Op.MINUS, item, new NumConst(1), ret));
                break;
        }
        curReg = ret;
    }

    private void ConstructNewArray(Stack<Register> dims, Register base, int current) {
        if (current == dims.size()) return;
        int id = arrayBlockCount++;
        var cond = new Block("array." + id + ".cond");
        var body = new Block("array." + id + ".body");
        var end = new Block("array." + id + ".end");
        var total = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.MINUS, dims.get(current - 1), new NumConst(1), total));
        var loop = new Local();
        curBlock.add(new AllocateStmt(loop));
        curBlock.add(new StoreStmt(total, loop));
        curBlock.add(new JmpStmt(cond));

        enterBlock(cond);
        var index = new Local();
        curBlock.add(new LoadStmt(loop, index));
        var offset = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.LSHIFT, index, new NumConst(2), offset));
        var location = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.PLUS, base, offset, location));
        var condition = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.GEQ, index, new NumConst(0), condition));
        curBlock.add(new BranchStmt(condition, body, end));

        enterBlock(body);
        var cur = new Local();
        var parameters = new ArrayList<Register>();
        parameters.add(dims.get(current));
        curBlock.add(new CallStmt(BuiltinFunction.mallocArray, parameters, cur));
        curBlock.add(new StoreStmt(cur, location));
        ConstructNewArray(dims, cur, current + 1);
        var next = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.MINUS, index, new NumConst(1), next));
        curBlock.add(new StoreStmt(next, loop));
        curBlock.add(new JmpStmt(cond));

        enterBlock(end);
    }

    private int arrayBlockCount = 0;
    @Override
    public void visit(NewExprNode node) {
        var ret = new Local();
        if (node.getNumDims() != 0) {
            Stack<Register> dims = new Stack<>();
            node.getDims().forEach(x -> {
                leftValueRequireStack.push(false);
                x.accept(this);
                leftValueRequireStack.pop();
                dims.push(curReg);
            });
            var parameters = new ArrayList<Register>();
            parameters.add(dims.get(0));
            curBlock.add(new CallStmt(BuiltinFunction.mallocArray, parameters, ret));
            ConstructNewArray(dims, ret, 1);
        } else {
            int typeSize;
            if (!(node.getNewType().getType() instanceof ClassType)) typeSize = 4;
            else typeSize = node.getNewType().getType().getEntity().getClassSize();
            var parameters_1 = new ArrayList<Register>();
            parameters_1.add(new NumConst(typeSize));
            curBlock.add(new CallStmt(BuiltinFunction.mallocObject, parameters_1, ret));
            if (node.getNewType().getType() instanceof ClassType) {
                var constructor = node.getNewType().getType().getEntity().getConstructor();
                if (constructor != null) {
                    var parameters_2 = new ArrayList<Register>();
                    parameters_2.add(ret);
                    curBlock.add(new CallStmt(functionMap.get(constructor), parameters_2, null));
                }
            }
        }
        curReg = ret;
    }

    private int shortCount = 0;
    @Override
    public void visit(BinaryExprNode node) {
        leftValueRequireStack.push(false);
        node.getLhs().accept(this);
        leftValueRequireStack.pop();
        Register lhs = curReg;

        if (node.getOp() == BinaryExprNode.Op.LOGIC_AND || node.getOp() == BinaryExprNode.Op.LOGIC_OR) {
            Block first = curBlock;
            Block second = new Block("short." + shortCount + ".second");
            Block end = new Block("short." + shortCount + ".end");
            if (node.getOp() == BinaryExprNode.Op.LOGIC_AND) {
                curBlock.add(new BranchStmt(lhs, end, second));
            } else {
                curBlock.add(new BranchStmt(lhs, second, end));
            }

            enterBlock(second);
            leftValueRequireStack.push(false);
            node.getRhs().accept(this);
            leftValueRequireStack.pop();
            Register rhs = curReg;
            curBlock.add(new JmpStmt(end));

            enterBlock(end);
            Local ret = new Local();
            if (node.getOp() == BinaryExprNode.Op.LOGIC_AND)
                curBlock.add(new PhiStmt(ret, Map.of(first, new NumConst(0), second, rhs)));
            else
                curBlock.add(new PhiStmt(ret, Map.of(first, new NumConst(1), second, rhs)));
            curReg = ret;
            shortCount++;
            return;
        }

        leftValueRequireStack.push(false);
        node.getRhs().accept(this);
        leftValueRequireStack.pop();
        Register rhs = curReg;
        Local ret = new Local();
        switch(node.getOp()){
            case EQ:
                curBlock.add(new OpStmt(OpStmt.Op.EQ, lhs, rhs, ret));
                break;
            case NEQ:
                curBlock.add(new OpStmt(OpStmt.Op.NEQ, lhs, rhs, ret));
                break;
            case LEQ:
                curBlock.add(new OpStmt(OpStmt.Op.LEQ, lhs, rhs, ret));
                break;
            case LTH:
                curBlock.add(new OpStmt(OpStmt.Op.LTH, lhs, rhs, ret));
                break;
            case GEQ:
                curBlock.add(new OpStmt(OpStmt.Op.GEQ, lhs, rhs, ret));
                break;
            case GTH:
                curBlock.add(new OpStmt(OpStmt.Op.GTH, lhs, rhs, ret));
                break;
            case ADD:
                curBlock.add(new OpStmt(OpStmt.Op.PLUS, lhs, rhs, ret));
                break;
            case SUB:
                curBlock.add(new OpStmt(OpStmt.Op.MINUS, lhs, rhs, ret));
                break;
            case MUL:
                curBlock.add(new OpStmt(OpStmt.Op.MUL, lhs, rhs, ret));
                break;
            case DIV:
                curBlock.add(new OpStmt(OpStmt.Op.DIV, lhs, rhs, ret));
                break;
            case MOD:
                curBlock.add(new OpStmt(OpStmt.Op.MOD, lhs, rhs, ret));
                break;
            case SLA:
                curBlock.add(new OpStmt(OpStmt.Op.LSHIFT, lhs, rhs, ret));
                break;
            case SRA:
                curBlock.add(new OpStmt(OpStmt.Op.ARSHIFT, lhs, rhs, ret));
                break;
            case BITWISE_OR:
                curBlock.add(new OpStmt(OpStmt.Op.OR, lhs, rhs, ret));
                break;
            case BITWISE_AND:
                curBlock.add(new OpStmt(OpStmt.Op.AND, lhs, rhs, ret));
                break;
            case BITWISE_XOR:
                curBlock.add(new OpStmt(OpStmt.Op.XOR, lhs, rhs, ret));
                break;
        }
        curReg = ret;
    }

    @Override
    public void visit(AssignExprNode node) {
        leftValueRequireStack.push(true);
        node.getLhs().accept(this);
        leftValueRequireStack.pop();
        Register lhs = curReg;

        leftValueRequireStack.push(false);
        node.getRhs().accept(this);
        leftValueRequireStack.pop();
        Register rhs = curReg;

        curBlock.add(new StoreStmt(rhs, lhs));
    }

    @Override
    public void visit(IdExprNode node) {
        if (node.getFuncEntity() == null) {
            Register ret;
            if (leftValueRequireStack.peek()) {
                ret = idMap.get(node.getEntity());
            } else {
                ret = new Local();
                curBlock.add(new LoadStmt(idMap.get(node.getEntity()), ret));
            }
            curReg = ret;
        } else {
            System.err.println("Error in IRBuilder!");
            // This node should not represent a function.
        }
    }

    @Override
    public void visit(ThisExprNode node) {
        curReg = idMap.get(node.getEntity());
    }

    @Override
    public void visit(NumConstNode node) {
        curReg = new NumConst(node.getVal());
    }

    @Override
    public void visit(StringConstNode node) {
        curReg = new StringConst(node.getStr());
    }

    @Override
    public void visit(BoolConstNode node) {
        if (node.getVal())
            curReg = new NumConst(1);
        else
            curReg = new NumConst(0);
    }

    @Override
    public void visit(NullConstNode node) {
        curReg = new NumConst(0);
    }

    @Override
    public void visit(TypeNode node) {
        // Need do nothing.
    }

    @Override
    public void visit(ArrayTypeNode node) {
        // Need do nothing.
    }
}