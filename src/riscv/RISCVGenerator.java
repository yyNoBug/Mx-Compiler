package riscv;

import ir.Block;
import ir.DeclaredFunction;
import ir.IRTop;
import ir.IRVisitor;
import ir.irStmt.*;
import ir.items.*;
import riscv.addr.Address;
import riscv.addr.ParaCallAddr;
import riscv.addr.ParaPassAddr;
import riscv.addr.StackAddr;
import riscv.instruction.*;
import riscv.register.REGISTER;
import riscv.register.SPILLED;
import riscv.register.VIRTUAL;

import java.util.LinkedHashMap;
import java.util.Map;

public class RISCVGenerator implements IRVisitor {
    private IRTop irTop;
    private RVTop rvTop = new RVTop();

    private RVFunction curFunction;
    private RVBlock curBlock;
    private DeclaredFunction curIRFunction;
    private Block curIRBlock;
    private boolean isPreScanning = false;

    private Map<Item, REGISTER> regMap = new LinkedHashMap<>();
    private Map<Block, RVBlock> blkMap = new LinkedHashMap<>();
    private Map<DeclaredFunction, RVBlock> functionExitBlkMap = new LinkedHashMap<>();
    private Map<PhiStmt, REGISTER> phiMap = new LinkedHashMap<>();

    public RISCVGenerator(IRTop irTop) {
        this.irTop = irTop;
        REGISTER.initialize();
    }

    public RVTop getRvTop() {
        return rvTop;
    }

    private void dealGlobal(Global global){
        curBlock = new RVBlock("global" + global.getNum());
        curFunction.add(curBlock);
        curBlock.add(new DirZERO(4));
    }

    private void dealStr(StringConst str){
        curBlock = new RVBlock("string" + str.getNum());
        curFunction.add(curBlock);
        curBlock.add(new DirWORD(str.getStr().length()));
        curBlock.add(new DirSTRING(str.getStr()));
    }

    public void generateRVAssembly() {
        curFunction = new RVFunction("__program_begin__");
        rvTop.add(curFunction);
        curBlock = new RVBlock("__invisible__");
        curFunction.add(curBlock);

        curBlock.add(new DirSECTION(".data"));
        var globals = irTop.getGlobals();
        globals.forEach(x -> dealGlobal(x));

        curBlock.add(new DirSECTION(".rodata"));
        var strs = irTop.getStrs();
        strs.forEach(x -> dealStr(x));

        curBlock.add(new DirSECTION("text"));
        curBlock.add(new DirGLOBL("main"));

        var declaredFunctions = irTop.getFunctions();
        isPreScanning = true;
        declaredFunctions.forEach(x -> visit(x));
        isPreScanning = false;
        declaredFunctions.forEach(x -> visit(x));
    }

    private REGISTER createReg(Item item) {
        if (item instanceof NumConst) {
            var ret = new VIRTUAL();
            curBlock.add(new LI(ret, ((NumConst) item).getValue()));
            return ret;
        }
        if (item instanceof StringConst) {
            var ret = new VIRTUAL();
            curBlock.add(new LA(ret, new RVString(((StringConst) item))));
            return ret;
        }
        if (item instanceof Local) {
            return regMap.get(item);
        }
        return null;
    }

    private void visit(DeclaredFunction function) {
        if (isPreScanning) {
            function.getBlockList().forEach(x -> visit(x));
            functionExitBlkMap.put(function,
                    new RVBlock("." + function.getName() + ".exit"));
            return;
        }

        int i = 0;
        for (Item arg : function.getArgs()) {
            regMap.put(arg, REGISTER.args[i++]);
            if (i == 8) break;
        }
        for (; i < function.getArgs().size(); ++i) {
            regMap.put(function.getArgs().get(i),
                    new SPILLED(new ParaCallAddr(i)));
        }

        curIRFunction = function;
        curFunction = new RVFunction(function);
        rvTop.add(curFunction);
        curBlock = new RVBlock("." + function.getName());
        curFunction.add(curBlock);

        curBlock.add(new SPGrow(curFunction));
        curBlock.add(new STORE(REGISTER.ra, new StackAddr(curFunction, curFunction.getTopIndex())));

        function.getBlockList().forEach(x -> visit(x));

        curBlock = functionExitBlkMap.get(function);
        curFunction.add(curBlock);
        curBlock.add(new LOAD(REGISTER.ra, new StackAddr(curFunction, 0)));
        curBlock.add(new SPRecover(curFunction));
        curBlock.add(new JR(REGISTER.ra));
    }

    private void visit(Block blk) {
        if (isPreScanning) {
            curBlock = new RVBlock(blk);
            blkMap.put(blk, curBlock);
            return;
        }

        curIRBlock = blk;
        curBlock = blkMap.get(blk);
        curFunction.add(curBlock);
        blk.getStmtList().forEach(x -> visit(x));
    }

    @Override
    public void visit(AllocateStmt stmt) {
        var tmp = new VIRTUAL();
        var addr = new StackAddr(curFunction, curFunction.getTopIndex());
        curBlock.add(new LAddr(tmp, addr));

        var dest = new VIRTUAL();
        regMap.put(stmt.getItem(), dest);
        curBlock.add(new Calc(OpClass.Op.ADD, dest, REGISTER.sp, tmp));
    }

    @Override
    public void visit(BranchStmt stmt) {
        var cond = stmt.getCondition();
        var thenBlock = stmt.getThenBlock();
        var elseBlock = stmt.getElseBlock();

        for (PhiStmt phiStmt : elseBlock.getPhiStmts()) {
            if (!phiMap.containsKey(phiStmt)) phiMap.put(phiStmt, new VIRTUAL());
            var phiReg = phiMap.get(phiStmt);
            Item item = phiStmt.getMap().get(curIRBlock);
            curBlock.add(new MV(phiReg, createReg(item)));
        }
        curBlock.add(new BEQZ(createReg(cond), blkMap.get(elseBlock)));

        for (PhiStmt phiStmt : thenBlock.getPhiStmts()) {
            if (!phiMap.containsKey(phiStmt)) phiMap.put(phiStmt, new VIRTUAL());
            var phiReg = phiMap.get(phiStmt);
            Item item = phiStmt.getMap().get(curIRBlock);
            curBlock.add(new MV(phiReg, createReg(item)));
        }
        curBlock.add(new J(blkMap.get(thenBlock)));
    }

    @Override
    public void visit(CallStmt stmt) {
        int size = stmt.getParameters().size();
        curFunction.setBottom(size - 7);
        for (int i = size - 1; i >= 8; --i) {
            curBlock.add(new STORE(createReg(stmt.getParameters().get(i)), new ParaPassAddr(i)));
        }
        for (int i = 0; i < size; ++i) {
            curBlock.add(new MV(REGISTER.args[i], createReg(stmt.getParameters().get(i))));
        }
        curBlock.add(new Call(stmt.getSymbol()));
        if (stmt.getResult() != null) {
            var dest = new VIRTUAL();
            regMap.put(stmt.getResult(), dest);
            curBlock.add(new MV(dest, REGISTER.args[0]));
        }
    }

    @Override
    public void visit(JmpStmt stmt) {
        for (PhiStmt phiStmt : stmt.getDestination().getPhiStmts()) {
            if (!phiMap.containsKey(phiStmt)) phiMap.put(phiStmt, new VIRTUAL());
            var phiReg = phiMap.get(phiStmt);
            Item item = phiStmt.getMap().get(curIRBlock);
            curBlock.add(new MV(phiReg, createReg(item)));
        }
        curBlock.add(new J(blkMap.get(stmt.getDestination())));
    }

    @Override
    public void visit(LoadStmt stmt) {
        var dest = new VIRTUAL();
        regMap.put(stmt.getDest(), dest);

        if (stmt.getSrc() instanceof Global) {
            curBlock.add(new LG(dest, new RVGlobal(((Global) stmt.getSrc()))));
        } else{
            curBlock.add(new LOAD(dest, new Address(regMap.get(stmt.getSrc()))));
        }
    }

    @Override
    public void visit(OpStmt stmt) {
        var dest = new VIRTUAL();
        regMap.put(stmt.getResult(), dest);

        var opr1 = stmt.getOpr1();
        var opr2 = stmt.getOpr2();

        switch(stmt.getOp()){
            case PLUS:
                curBlock.add(new Calc(OpClass.Op.ADD, dest, createReg(opr1), createReg(opr2)));
                break;
            case MINUS:
                curBlock.add(new Calc(OpClass.Op.SUB, dest, createReg(opr1), createReg(opr2)));
                break;
            case MUL:
                curBlock.add(new Calc(OpClass.Op.MUL, dest, createReg(opr1), createReg(opr2)));
                break;
            case DIV:
                curBlock.add(new Calc(OpClass.Op.DIV, dest, createReg(opr1), createReg(opr2)));
                break;
            case MOD:
                curBlock.add(new Calc(OpClass.Op.REM, dest, createReg(opr1), createReg(opr2)));
                break;
            case OR:
                curBlock.add(new Calc(OpClass.Op.OR, dest, createReg(opr1), createReg(opr2)));
                break;
            case AND:
                curBlock.add(new Calc(OpClass.Op.AND, dest, createReg(opr1), createReg(opr2)));
                break;
            case XOR:
                curBlock.add(new Calc(OpClass.Op.XOR, dest, createReg(opr1), createReg(opr2)));
                break;
            case LSHIFT:
                curBlock.add(new Calc(OpClass.Op.SLL, dest, createReg(opr1), createReg(opr2)));
                break;
            case ARSHIFT:
                curBlock.add(new Calc(OpClass.Op.SRA, dest, createReg(opr1), createReg(opr2)));
                break;
            case RSHIFT:
                curBlock.add(new Calc(OpClass.Op.SRL, dest, createReg(opr1), createReg(opr2)));
                break;
            case EQ:
                REGISTER difference;
                difference = new VIRTUAL();
                curBlock.add(new Calc(OpClass.Op.SUB, difference, createReg(opr1), createReg(opr2)));
                curBlock.add(new SetZ(OpClass.Cmp.EQ, dest, difference));
                break;
            case NEQ:
                difference = new VIRTUAL();
                curBlock.add(new Calc(OpClass.Op.SUB, difference, createReg(opr1), createReg(opr2)));
                curBlock.add(new SetZ(OpClass.Cmp.NEQ, dest, difference));
                break;
            case LTH:
                difference = new VIRTUAL();
                curBlock.add(new Calc(OpClass.Op.SUB, difference, createReg(opr1), createReg(opr2)));
                curBlock.add(new SetZ(OpClass.Cmp.LT, dest, difference));
                break;
            case GTH:
                difference = new VIRTUAL();
                curBlock.add(new Calc(OpClass.Op.SUB, difference, createReg(opr1), createReg(opr2)));
                curBlock.add(new SetZ(OpClass.Cmp.GT, dest, difference));
                break;
            case LEQ:
                REGISTER opposite;
                difference = new VIRTUAL();
                curBlock.add(new Calc(OpClass.Op.SUB, difference, createReg(opr1), createReg(opr2)));
                opposite = new VIRTUAL();
                curBlock.add(new SetZ(OpClass.Cmp.GT, opposite, difference));
                curBlock.add(new CalcI(OpClass.Op.XOR, dest, opposite, 1));
                break;
            case GEQ:
                difference = new VIRTUAL();
                curBlock.add(new Calc(OpClass.Op.SUB, difference, createReg(opr1), createReg(opr2)));
                opposite = new VIRTUAL();
                curBlock.add(new SetZ(OpClass.Cmp.LT, opposite, difference));
                curBlock.add(new CalcI(OpClass.Op.XOR, dest, opposite, 1));
                break;
        }
    }

    @Override
    public void visit(PhiStmt stmt) {
        var dest = new VIRTUAL();
        regMap.put(stmt.getTarget(), dest);
        curBlock.add(new MV(dest, phiMap.get(stmt)));
    }

    @Override
    public void visit(RetStmt stmt) {
        if (stmt.getItem() != null)
            curBlock.add(new MV(REGISTER.args[0], createReg(stmt.getItem())));
        curBlock.add(new J(functionExitBlkMap.get(curIRFunction)));
    }

    @Override
    public void visit(StoreStmt stmt) {
        if (stmt.getDest() instanceof Global) {
            curBlock.add(new SG(createReg(stmt.getSrc()), new RVGlobal(((Global) stmt.getDest())), new VIRTUAL()));
        } else{
            curBlock.add(new STORE(createReg(stmt.getSrc()), new Address(regMap.get(stmt.getDest()))));
        }
    }
}
