package riscv;

import ir.Block;
import ir.DeclaredFunction;
import ir.IRTop;
import ir.IRVisitor;
import ir.irStmt.*;
import ir.items.*;
import riscv.addr.Address;
import riscv.addr.ParaPassAddr;
import riscv.addr.StackAddr;
import riscv.instruction.*;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.LinkedHashMap;
import java.util.Map;

public class RISCVGenerator implements IRVisitor {
    private IRTop irTop;
    private RVTop rvTop = new RVTop();

    private RVFunction curFunction;
    private RVBlock curBlock;
    private boolean isPreScanning = false;

    private Map<Item, REGISTER> regMap = new LinkedHashMap<>();
    private Map<Block, RVBlock> blkMap = new LinkedHashMap<>();

    public RISCVGenerator(IRTop irTop) {
        this.irTop = irTop;
        REGISTER.initialize();
    }

    public RVTop getRvTop() {
        return rvTop;
    }

    private void dealGlobal(Global global){
        curBlock = new RVBlock(global.toString());
        curFunction.add(curBlock);
        curBlock.add(new DirZERO(4));
    }

    private void dealStr(StringConst str){
        curBlock = new RVBlock("string" + str.getNumber());
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
            curBlock.add(new LI(ret, item.getNum()));
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
            return;
        }

        curFunction = new RVFunction(function);
        rvTop.add(curFunction);
        curBlock = new RVBlock("." + function.getName());
        curFunction.add(curBlock);

        curBlock.add(new SPGrow(curFunction));
        curBlock.add(new STORE(REGISTER.ra, new StackAddr(curFunction, curFunction.getTopIndex())));

        function.getBlockList().forEach(x -> visit(x));

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

        curBlock = blkMap.get(blk);
        curFunction.add(curBlock);
        blk.getStmtList().forEach(x -> visit(x));
    }

    @Override
    public void visit(AllocateStmt stmt) {
        var dest = new VIRTUAL();
        regMap.put(stmt.getItem(), dest);
        var addr = new StackAddr(curFunction, curFunction.getTopIndex());
        curBlock.add(new LAddr(dest, addr));
    }

    @Override
    public void visit(BranchStmt stmt) {
        var cond = stmt.getCondition();
        var thenBlock = stmt.getThenBlock();
        var elseBlock = stmt.getElseBlock();

        curBlock.add(new BEQZ(createReg(cond), blkMap.get(elseBlock)));
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
        if (opr1 instanceof NumConst && opr2 instanceof NumConst) {
            var n1 = opr1.getNum();
            var n2 = opr2.getNum();
            int ans;
            switch (stmt.getOp()) {
                case PLUS:
                    ans = n1 + n2;
                    break;
                case MINUS:
                    ans = n1 - n2;
                    break;
                case MUL:
                    ans = n1 * n2;
                    break;
                case DIV:
                    ans = n1 / n2;
                    break;
                case MOD:
                    ans = n1 % n2;
                    break;
                case OR:
                    ans = n1 | n2;
                    break;
                case AND:
                    ans = n1 & n2;
                    break;
                case XOR:
                    ans = n1 ^ n2;
                    break;
                case LSHIFT:
                    ans = n1 << n2;
                    break;
                case ARSHIFT:
                    ans = n1 >> n2;
                    break;
                case RSHIFT:
                    ans = n1 >>> n2;
                    break;
                case EQ:
                    ans = n1 == n2 ? 1 : 0;
                    break;
                case NEQ:
                    ans = n1 != n2 ? 1 : 0;
                    break;
                case GEQ:
                    ans = n1 >= n2 ? 1: 0;
                    break;
                case GTH:
                    ans = n1 > n2 ? 1 : 0;
                    break;
                case LEQ:
                    ans = n1 <= n2 ? 1 : 0;
                    break;
                case LTH:
                    ans = n1 < n2 ? 1 : 0;
                    break;
                default:
                    ans = 0;
                    break;
            }
            curBlock.add(new LI(dest, ans));
        }

        if (opr1 instanceof NumConst && opr2 instanceof Local) {
            var tmp = opr1;
            opr1 = opr2;
            opr2 = tmp;
        }

        if (opr2 instanceof NumConst) {
            var imm = opr2.getNum();
            VIRTUAL temp;
            switch(stmt.getOp()){
                case PLUS:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, imm));
                        curBlock.add(new Calc(OpClass.Op.ADD, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.ADD, dest, regMap.get(opr1), imm));
                    break;
                case MINUS:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, -imm));
                        curBlock.add(new Calc(OpClass.Op.ADD, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.ADD, dest, regMap.get(opr1), -imm));
                    break;
                case MUL:
                    temp = new VIRTUAL();
                    curBlock.add(new LI(temp, imm));
                    curBlock.add(new Calc(OpClass.Op.MUL, dest, regMap.get(opr1), temp));
                    break;
                case DIV:
                    temp = new VIRTUAL();
                    curBlock.add(new LI(temp, imm));
                    curBlock.add(new Calc(OpClass.Op.DIV, dest, regMap.get(opr1), temp));
                    break;
                case MOD:
                    temp = new VIRTUAL();
                    curBlock.add(new LI(temp, imm));
                    curBlock.add(new Calc(OpClass.Op.REM, dest, regMap.get(opr1), temp));
                    break;
                case OR:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, imm));
                        curBlock.add(new Calc(OpClass.Op.OR, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.OR, dest, regMap.get(opr1), imm));
                    break;
                case AND:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, imm));
                        curBlock.add(new Calc(OpClass.Op.AND, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.AND, dest, regMap.get(opr1), imm));
                    break;
                case XOR:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, imm));
                        curBlock.add(new Calc(OpClass.Op.XOR, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.XOR, dest, regMap.get(opr1), imm));
                    break;
                case LSHIFT:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, imm));
                        curBlock.add(new Calc(OpClass.Op.SLL, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.SLL, dest, regMap.get(opr1), imm));
                    break;
                case ARSHIFT:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, imm));
                        curBlock.add(new Calc(OpClass.Op.SRA, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.SRA, dest, regMap.get(opr1), imm));
                    break;
                case RSHIFT:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, imm));
                        curBlock.add(new Calc(OpClass.Op.SRL, dest, regMap.get(opr1), temp));
                    }
                    curBlock.add(new CalcI(OpClass.Op.SRL, dest, regMap.get(opr1), imm));
                    break;
                case EQ:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, -imm));
                        var temp2 = new VIRTUAL();
                        curBlock.add(new Calc(OpClass.Op.ADD, temp2, regMap.get(opr1), temp));
                        curBlock.add(new SetZ(OpClass.Cmp.EQ, dest, temp2));
                    }
                    temp = new VIRTUAL();
                    curBlock.add(new CalcI(OpClass.Op.ADD, temp, regMap.get(opr1), -imm));
                    curBlock.add(new SetZ(OpClass.Cmp.EQ, dest, temp));
                    break;
                case NEQ:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, -imm));
                        var temp2 = new VIRTUAL();
                        curBlock.add(new Calc(OpClass.Op.ADD, temp2, regMap.get(opr1), temp));
                        curBlock.add(new SetZ(OpClass.Cmp.NEQ, dest, temp2));
                    }
                    temp = new VIRTUAL();
                    curBlock.add(new CalcI(OpClass.Op.ADD, temp, regMap.get(opr1), -imm));
                    curBlock.add(new SetZ(OpClass.Cmp.NEQ, dest, temp));
                    break;
                case GEQ:
                    temp = new VIRTUAL();
                    curBlock.add(new LI(temp, imm));
                    var tempGEQ = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, tempGEQ, temp, regMap.get(opr1)));
                    curBlock.add(new SetZ(OpClass.Cmp.LT, dest, tempGEQ));
                    break;
                case GTH:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, -imm));
                        var temp2 = new VIRTUAL();
                        curBlock.add(new Calc(OpClass.Op.ADD, temp2, regMap.get(opr1), temp));
                        curBlock.add(new SetZ(OpClass.Cmp.GT, dest, temp2));
                    }
                    temp = new VIRTUAL();
                    curBlock.add(new CalcI(OpClass.Op.ADD, temp, regMap.get(opr1), -imm));
                    curBlock.add(new SetZ(OpClass.Cmp.GT, dest, temp));
                    break;
                case LEQ:
                    temp = new VIRTUAL();
                    curBlock.add(new LI(temp, imm));
                    var tempLEQ = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, tempLEQ, temp, regMap.get(opr1)));
                    curBlock.add(new SetZ(OpClass.Cmp.GT, dest, tempLEQ));
                    break;
                case LTH:
                    if (imm >= 2048 || imm < -2048) {
                        temp = new VIRTUAL();
                        curBlock.add(new LI(temp, -imm));
                        var temp2 = new VIRTUAL();
                        curBlock.add(new Calc(OpClass.Op.ADD, temp2, regMap.get(opr1), temp));
                        curBlock.add(new SetZ(OpClass.Cmp.LT, dest, temp2));
                    }
                    temp = new VIRTUAL();
                    curBlock.add(new CalcI(OpClass.Op.ADD, temp, regMap.get(opr1), -imm));
                    curBlock.add(new SetZ(OpClass.Cmp.LT, dest, temp));
                    break;
            }
        } else {
            REGISTER temp;
            switch(stmt.getOp()){
                case PLUS:
                    curBlock.add(new Calc(OpClass.Op.ADD, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case MINUS:
                    curBlock.add(new Calc(OpClass.Op.SUB, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case MUL:
                    curBlock.add(new Calc(OpClass.Op.MUL, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case DIV:
                    curBlock.add(new Calc(OpClass.Op.DIV, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case MOD:
                    curBlock.add(new Calc(OpClass.Op.REM, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case OR:
                    curBlock.add(new Calc(OpClass.Op.OR, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case AND:
                    curBlock.add(new Calc(OpClass.Op.AND, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case XOR:
                    curBlock.add(new Calc(OpClass.Op.XOR, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case LSHIFT:
                    curBlock.add(new Calc(OpClass.Op.SLL, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case ARSHIFT:
                    curBlock.add(new Calc(OpClass.Op.SRA, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case RSHIFT:
                    curBlock.add(new Calc(OpClass.Op.SRL, dest, regMap.get(opr1), regMap.get(opr2)));
                    break;
                case EQ:
                    temp = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, temp, regMap.get(opr1), regMap.get(opr2)));
                    curBlock.add(new SetZ(OpClass.Cmp.EQ, dest, temp));
                    break;
                case NEQ:
                    temp = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, temp, regMap.get(opr1), regMap.get(opr2)));
                    curBlock.add(new SetZ(OpClass.Cmp.NEQ, dest, temp));
                    break;
                case GEQ:
                    temp = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, temp, regMap.get(opr2), regMap.get(opr1)));
                    curBlock.add(new SetZ(OpClass.Cmp.LT, dest, temp));
                    break;
                case GTH:
                    temp = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, temp, regMap.get(opr1), regMap.get(opr2)));
                    curBlock.add(new SetZ(OpClass.Cmp.GT, dest, temp));
                    break;
                case LEQ:
                    temp = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, temp, regMap.get(opr2), regMap.get(opr1)));
                    curBlock.add(new SetZ(OpClass.Cmp.GT, dest, temp));
                    break;
                case LTH:
                    temp = new VIRTUAL();
                    curBlock.add(new Calc(OpClass.Op.SUB, temp, regMap.get(opr1), regMap.get(opr2)));
                    curBlock.add(new SetZ(OpClass.Cmp.LT, dest, temp));
                    break;
            }
        }
    }

    @Override
    public void visit(PhiStmt stmt) {
        var phiReg = new VIRTUAL();
        stmt.getMap().forEach( (x, y) -> {
            blkMap.get(x).add(new MV(phiReg, createReg(y)));
        });
        var dest = new VIRTUAL();
        regMap.put(stmt.getTarget(), dest);
        curBlock.add(new MV(dest, phiReg));
    }

    @Override
    public void visit(RetStmt stmt) {
        curBlock.add(new MV(REGISTER.args[0], createReg(stmt.getItem())));
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
