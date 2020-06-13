package frontend;

import ast.*;
import ir.*;
import ir.irStmt.*;
import ir.items.*;
import ir.maps.FunctionMap;
import ir.maps.GlobalMap;
import ir.maps.IdMap;
import ir.maps.LoopMap;
import scope.DefinedClass;
import scope.DefinedVariable;
import scope.TopLevelScope;
import type.ClassType;
import type.StringType;
import type.VoidType;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class IRBuilder implements ASTVisitor {
    private IRTop top = new IRTop();

    private DeclaredFunction curFunction;
    private Block curBlock;
    private Item curReg;
    private DefinedClass curClassEntity;
    private Local curThisPointer;
    private Stack<Boolean> leftValueRequireStack = new Stack<>();
    private boolean isVisitingGlobalVariable;
    private boolean isVisitingParameter;

    private IdMap idMap = new IdMap();
    private GlobalMap globalMap = new GlobalMap();
    private LoopMap loopMap = new LoopMap();
    private FunctionMap functionMap = new FunctionMap();


    public IRBuilder(TopLevelScope globalScope) {
        BuiltinFunction.setFunctionMap(functionMap, globalScope);
    }

    public void printIR(){
        try {
            FileWriter fileWriter = new FileWriter("ir.out");
            PrintWriter writer = new PrintWriter(fileWriter);
            var map = globalMap.getGlobalList();
            for (Global global : top.getGlobals()) {
                writer.println("global " + global);
            }

            for (StringConst str : top.getStrs()) {
                writer.println("string " + str + " = \"" + str.getEscapedString() + "\"");
            }

            writer.println();

            var declaredFunctions = top.getFunctions();
            for (DeclaredFunction function : declaredFunctions) {
                function.printIR(writer);
                writer.println();
            }

            writer.close();
        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public IRTop getTop() {
        return top;
    }

    private void enterBlock (Block block) {
        curFunction.add(block);
        curBlock = block;
    }

    @Override
    public void visit(ProgramNode node) {
        isVisitingGlobalVariable = true;
        for (DeclarationNode declarationNode: node.getSection_list()) {
            if (declarationNode instanceof ClassDeclNode)
                curClassEntity = ((ClassDeclNode) declarationNode).getEntity();
            else curClassEntity = null;
            declarationNode.accept(this);
        }

        isVisitingGlobalVariable = false;
        for (DeclarationNode declarationNode : node.getSection_list()) {
            if (!(declarationNode instanceof VarDeclSingleNode)) {
                if (declarationNode instanceof ClassDeclNode)
                    curClassEntity = ((ClassDeclNode) declarationNode).getEntity();
                else curClassEntity = null;
                declarationNode.accept(this);
            }
        }
    }

    @Override
    public void visit(VarDeclSingleNode node) {
        if (isVisitingGlobalVariable) {
            Global global = new Global();
            top.add(global);
            idMap.put(node.getEntity(), global);
            globalMap.add(node.getEntity(), node.getExpr());
        } else {
            if (isVisitingParameter) {
                Local parameterValue = new Local();
                curFunction.defineArg(parameterValue);
                Local local = new Local();
                idMap.put(node.getEntity(), local);
                curBlock.add(new AllocateStmt(local));
                curBlock.add(new StoreStmt(parameterValue, local));
                return;
            }

            Local local = new Local();
            idMap.put(node.getEntity(), local);
            curBlock.add(new AllocateStmt(local));

            if (node.getExpr() != null) {
                leftValueRequireStack.push(false);
                node.getExpr().accept(this);
                leftValueRequireStack.pop();
                curBlock.add(new StoreStmt(curReg, local));
            } /*else {
                curBlock.add(new StoreStmt(new NumConst(0), local));
            }*/
        }
    }

    @Override
    public void visit(FunDeclNode node) {
        if (isVisitingGlobalVariable) {
            String name;
            if (curClassEntity == null) name = node.getName();
            else name = curClassEntity.getName() + "." + node.getName();
            curFunction = new DeclaredFunction(name);
            top.add(curFunction);
            functionMap.put(node.getEntity(), curFunction);
            return;
        }

        curFunction = ((DeclaredFunction) functionMap.get(node.getEntity()));
        Block entry = new Block(curFunction.getName() + ".entry");

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
        if (curClassEntity != null) {
            Local parameterValue = new Local();
            curFunction.defineArg(parameterValue);
            Local local = new Local();
            curThisPointer = local;
            curBlock.add(new AllocateStmt(local));
            curBlock.add(new StoreStmt(parameterValue, local));
        }
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
        if (node.getConstructor() != null) node.getConstructor().accept(this);
        node.getMemberFuns().forEach(x->x.accept(this));
    }

    @Override
    public void visit(ClassConstructorNode node) {
        if (isVisitingGlobalVariable) {
            curFunction = new DeclaredFunction(node.getName() + ".__constructor__");
            top.add(curFunction);
            functionMap.put(node.getEntity(), curFunction);
            return;
        }

        curFunction = ((DeclaredFunction) functionMap.get(node.getEntity()));
        enterBlock(new Block(curFunction.getName() + ".entry"));

        Local parameterValue = new Local();
        curFunction.defineArg(parameterValue);
        Local local = new Local();
        curThisPointer = local;
        curBlock.add(new AllocateStmt(local));
        curBlock.add(new StoreStmt(parameterValue, local));

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
        if (node.getThenStatement() != null)
            node.getThenStatement().accept(this);
        curBlock.add(new JmpStmt(endBlock));

        enterBlock(elseBlock);
        if (node.getElseStatement() != null)
            node.getElseStatement().accept(this);
        curBlock.add(new JmpStmt(endBlock));

        enterBlock(endBlock);
    }

    private int whileCount = 0;
    @Override
    public void visit(WhileStatementNode node) {
        Block conditionBlock = new Block("while." + whileCount + ".condition");
        Block bodyBlock = new Block("while." + whileCount + ".body");
        Block endBlock = new Block("while." + whileCount + ".end");
        whileCount++;
        loopMap.put(node, conditionBlock, endBlock);
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
    }

    private int forCount = 0;
    @Override
    public void visit(ForStatementNode node) {
        if (node.getInit() != null) node.getInit().accept(this);
        Block conditionBlock = new Block("for." + forCount + ".condition");
        Block bodyBlock = new Block("for." + forCount + ".body");
        Block stepBlock = new Block("for." + forCount + ".step");
        Block endBlock = new Block("for." + forCount + ".end");
        forCount++;
        loopMap.put(node, stepBlock, endBlock);
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
    }

    @Override
    public void visit(ContinueStatementNode node) {
        curBlock.add(new JmpStmt(loopMap.getStep(node.getLoopNode())));
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
        ArrayList<Item> parameters = new ArrayList<>();
        if (node.getFuncExpr().getFuncEntity().isMemberFunction()) {
            if (node.getFuncExpr() instanceof MemberExprNode) {
                leftValueRequireStack.add(true);
                node.getFuncExpr().accept(this);
                leftValueRequireStack.pop();
                parameters.add(curReg);
            } else {
                Local thisPointer = new Local();
                curBlock.add(new LoadStmt(curThisPointer, thisPointer));
                parameters.add(thisPointer);
            }
        }
        node.getParameterList().forEach(x -> {
            leftValueRequireStack.add(false);
            x.accept(this);
            leftValueRequireStack.pop();
            parameters.add(curReg);
        });

        if (!(node.getFuncExpr().getFuncEntity().getType() instanceof VoidType)){
            Local result = new Local();
            curBlock.add(new CallStmt(function, parameters, result));
            curReg = result;
        } else {
            curBlock.add(new CallStmt(function, parameters, null));
            curReg = null;
        }
    }

    @Override
    public void visit(IdExprNode node) {
        if (node.getFuncEntity() == null) {
            if (leftValueRequireStack.empty()) return;
            Item ret;
            if (leftValueRequireStack.peek()) {
                ret = idMap.get(node.getEntity());
                if (ret == null) {
                    Item offset = new NumConst(curClassEntity.calOffset(node.getId()) * 4);
                    Item thisPointer = new Local();
                    curBlock.add(new LoadStmt(curThisPointer, thisPointer));
                    ret = new Local();
                    curBlock.add(new OpStmt(OpStmt.Op.PLUS, thisPointer, offset, ret));
                }
            } else {
                ret = new Local();
                var x = idMap.get(node.getEntity());
                if (x == null) {
                    Item offset = new NumConst(curClassEntity.calOffset(node.getId()) * 4);
                    Item thisPointer = new Local();
                    curBlock.add(new LoadStmt(curThisPointer, thisPointer));
                    x = new Local();
                    curBlock.add(new OpStmt(OpStmt.Op.PLUS, thisPointer, offset, x));
                }
                curBlock.add(new LoadStmt(x, ret));
            }
            curReg = ret;
        } else {
            System.err.println("Error in IRBuilder!");
            // This node should not represent a function.
        }
    }

    @Override
    public void visit(ArrayExprNode node) {
        leftValueRequireStack.push(false);
        node.getArray().accept(this);
        leftValueRequireStack.pop();
        Item base = curReg;

        leftValueRequireStack.push(false);
        node.getIndex().accept(this);
        leftValueRequireStack.pop();
        Item ind = curReg;
        Local offset = new Local();
        int size = 2;
        curBlock.add(new OpStmt(OpStmt.Op.LSHIFT, ind, new NumConst(size), offset));
        Local ret = new Local();
        curBlock.add(new OpStmt(OpStmt.Op.PLUS, base, offset, ret));
        if (leftValueRequireStack.empty()) return;
        if (!leftValueRequireStack.peek()) {
            Local src = ret;
            ret = new Local();
            curBlock.add(new LoadStmt(src, ret));
        }
        curReg = ret;
    }

    @Override
    public void visit(MemberExprNode node) {
        leftValueRequireStack.add(false);
        node.getExpr().accept(this);
        leftValueRequireStack.pop();
        Item parent = curReg;
        Item ret;
        if (node.getFuncEntity() != null) {
            ret = parent;
        } else {
            Item offset = new NumConst(node.getExpr().getType().getEntity().calOffset(node.getMember()) * 4);
            ret = new Local();
            curBlock.add(new OpStmt(OpStmt.Op.PLUS, parent, offset, ret));
        }
        if (leftValueRequireStack.empty()) return;
        if (!leftValueRequireStack.peek()) {
            var src = ret;
            ret = new Local();
            curBlock.add(new LoadStmt(src, ret));
        }
        curReg = ret;
    }

    @Override
    public void visit(PrefixExprNode node) {
        leftValueRequireStack.push(false);
        node.getExpr().accept(this);
        leftValueRequireStack.pop();
        Item item = curReg;

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
                leftValueRequireStack.push(true);
                node.getExpr().accept(this);
                leftValueRequireStack.pop();
                curBlock.add(new StoreStmt(ret, curReg));
                break;
            case PREFIX_DEC:
                curBlock.add(new OpStmt(OpStmt.Op.MINUS, item, new NumConst(1), ret));
                leftValueRequireStack.push(true);
                node.getExpr().accept(this);
                leftValueRequireStack.pop();
                curBlock.add(new StoreStmt(ret, curReg));
                break;
        }
        curReg = ret;
    }

    @Override
    public void visit(SuffixExprNode node) {
        leftValueRequireStack.push(false);
        node.getExpr().accept(this);
        leftValueRequireStack.pop();
        Item ret = curReg;

        Item item = new Local();
        switch (node.getOp()) {
            case SUFFIX_INC:
                curBlock.add(new OpStmt(OpStmt.Op.PLUS, curReg, new NumConst(1), item));
                leftValueRequireStack.push(true);
                node.getExpr().accept(this);
                leftValueRequireStack.pop();
                curBlock.add(new StoreStmt(item, curReg));
                break;
            case SUFFIX_DEC:
                curBlock.add(new OpStmt(OpStmt.Op.MINUS, curReg, new NumConst(1), item));
                leftValueRequireStack.push(true);
                node.getExpr().accept(this);
                leftValueRequireStack.pop();
                curBlock.add(new StoreStmt(item, curReg));
                break;
        }
        curReg = ret;
    }

    private void ConstructNewArray(Stack<Item> dims, Item base, int current) {
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
        var parameters = new ArrayList<Item>();
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
            Stack<Item> dims = new Stack<>();
            node.getDims().forEach(x -> {
                leftValueRequireStack.push(false);
                x.accept(this);
                leftValueRequireStack.pop();
                dims.push(curReg);
            });
            var parameters = new ArrayList<Item>();
            parameters.add(dims.get(0));
            curBlock.add(new CallStmt(BuiltinFunction.mallocArray, parameters, ret));
            ConstructNewArray(dims, ret, 1);
        } else {
            int typeSize;
            if (!(node.getNewType().getType() instanceof ClassType)) typeSize = 4;
            else typeSize = node.getNewType().getType().getEntity().getClassSize();
            var parameters_1 = new ArrayList<Item>();
            parameters_1.add(new NumConst(typeSize));
            curBlock.add(new CallStmt(BuiltinFunction.mallocObject, parameters_1, ret));
            if (node.getNewType().getType() instanceof ClassType) {
                var constructor = node.getNewType().getType().getEntity().getConstructor();
                if (constructor != null) {
                    var parameters_2 = new ArrayList<Item>();
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
        Item lhs = curReg;

        if (node.getOp() == BinaryExprNode.Op.LOGIC_AND || node.getOp() == BinaryExprNode.Op.LOGIC_OR) {
            Block first = curBlock;
            Block second = new Block("short." + shortCount + ".second");
            Block end = new Block("short." + shortCount + ".end");
            if (node.getOp() == BinaryExprNode.Op.LOGIC_OR) {
                curBlock.add(new BranchStmt(lhs, end, second));
            } else {
                curBlock.add(new BranchStmt(lhs, second, end));
            }

            enterBlock(second);
            leftValueRequireStack.push(false);
            node.getRhs().accept(this);
            leftValueRequireStack.pop();
            Item rhs = curReg;
            curBlock.add(new JmpStmt(end));

            enterBlock(end);
            Local ret = new Local();
            if (node.getOp() == BinaryExprNode.Op.LOGIC_AND) {
                var map = new HashMap<Block, Item>(){{
                   put(first, new NumConst(0));
                   put(second, rhs);
                }};
                curBlock.add(new PhiStmt(ret, map));
                //curBlock.add(new PhiStmt(ret, Map.of(first, new NumConst(0), second, rhs)));
            }
            else {
                var map = new HashMap<Block, Item>(){{
                    put(first, new NumConst(1));
                    put(second, rhs);
                }};
                curBlock.add(new PhiStmt(ret, map));
                //curBlock.add(new PhiStmt(ret, Map.of(first, new NumConst(1), second, rhs)));
            }
            curReg = ret;
            shortCount++;
            return;
        }

        leftValueRequireStack.push(false);
        node.getRhs().accept(this);
        leftValueRequireStack.pop();
        Item rhs = curReg;
        Local ret = new Local();
        switch(node.getOp()){
            case EQ:
                if (node.getLhs().getType() instanceof StringType) {
                    var para = new ArrayList<Item>();
                    para.add(lhs);
                    para.add(rhs);
                    curBlock.add(new CallStmt(BuiltinFunction.stringEqual, para, ret));
                } else {
                    curBlock.add(new OpStmt(OpStmt.Op.EQ, lhs, rhs, ret));
                }
                break;
            case NEQ:
                if (node.getLhs().getType() instanceof StringType) {
                    var para = new ArrayList<Item>();
                    para.add(lhs);
                    para.add(rhs);
                    curBlock.add(new CallStmt(BuiltinFunction.stringNeq, para, ret));
                } else {
                    curBlock.add(new OpStmt(OpStmt.Op.NEQ, lhs, rhs, ret));
                }
                break;
            case LEQ:
                if (node.getLhs().getType() instanceof StringType) {
                    var para = new ArrayList<Item>();
                    para.add(lhs);
                    para.add(rhs);
                    curBlock.add(new CallStmt(BuiltinFunction.stringLeq, para, ret));
                } else {
                    curBlock.add(new OpStmt(OpStmt.Op.LEQ, lhs, rhs, ret));
                }
                break;
            case LTH:
                if (node.getLhs().getType() instanceof StringType) {
                    var para = new ArrayList<Item>();
                    para.add(lhs);
                    para.add(rhs);
                    curBlock.add(new CallStmt(BuiltinFunction.stringLess, para, ret));
                } else {
                    curBlock.add(new OpStmt(OpStmt.Op.LTH, lhs, rhs, ret));
                }
                break;
            case GEQ:
                if (node.getLhs().getType() instanceof StringType) {
                    var para = new ArrayList<Item>();
                    para.add(lhs);
                    para.add(rhs);
                    curBlock.add(new CallStmt(BuiltinFunction.stringGeq, para, ret));
                } else {
                    curBlock.add(new OpStmt(OpStmt.Op.GEQ, lhs, rhs, ret));
                }
                break;
            case GTH:
                if (node.getLhs().getType() instanceof StringType) {
                    var para = new ArrayList<Item>();
                    para.add(lhs);
                    para.add(rhs);
                    curBlock.add(new CallStmt(BuiltinFunction.stringGreater, para, ret));
                } else {
                    curBlock.add(new OpStmt(OpStmt.Op.GTH, lhs, rhs, ret));
                }
                break;
            case ADD:
                if (node.getLhs().getType() instanceof StringType) {
                    var para = new ArrayList<Item>();
                    para.add(lhs);
                    para.add(rhs);
                    curBlock.add(new CallStmt(BuiltinFunction.stringConcatenate, para, ret));
                } else {
                    curBlock.add(new OpStmt(OpStmt.Op.PLUS, lhs, rhs, ret));
                }
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
        Item lhs = curReg;

        leftValueRequireStack.push(false);
        node.getRhs().accept(this);
        leftValueRequireStack.pop();
        Item rhs = curReg;

        curBlock.add(new StoreStmt(rhs, lhs));
    }

    @Override
    public void visit(ThisExprNode node) {
        Local thisPointer = new Local();
        curBlock.add(new LoadStmt(curThisPointer, thisPointer));
        curReg = thisPointer;
    }

    @Override
    public void visit(NumConstNode node) {
        curReg = new NumConst(node.getVal());
    }

    @Override
    public void visit(StringConstNode node) {
        curReg = new StringConst(node.getStr());
        top.add(((StringConst) curReg));
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

    @Override
    public void visit(VarDeclStatementNode node) {
        // Need do nothing.
    }
}