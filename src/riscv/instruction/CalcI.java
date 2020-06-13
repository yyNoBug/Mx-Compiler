package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class CalcI extends Instruction {
    private OpClass.Op op;
    private REGISTER dest, lhs;
    private int imm;

    public CalcI(OpClass.Op op, REGISTER dest, REGISTER lhs, int imm) {
        this.op = op;
        this.dest = dest;
        this.lhs = lhs;
        this.imm = imm;
    }

    public OpClass.Op getOp() {
        return op;
    }

    public REGISTER getDest() {
        return dest;
    }

    public REGISTER getLhs() {
        return lhs;
    }

    public int getImm() {
        return imm;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        lhs = super.resolveSrc(virtualMap, itr, lhs, 3);
        dest = super.resolveDest(virtualMap, itr, dest, function,5);
    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>(){{add(dest);}};
    }

    @Override
    public HashSet<REGISTER> getUses() {
        return new HashSet<>(){{add(lhs);}};
    }

    @Override
    public void replaceUse(REGISTER old, REGISTER newReg) {
        if (lhs == old) lhs = newReg;
    }

    @Override
    public void replaceRd(REGISTER old, REGISTER newReg) {
        if (dest == old) dest = newReg;
    }

    @Override
    public String toString() {
        switch (op) {
            case ADD:
                return "\taddi\t" + dest + "," + lhs + "," + imm;
            case OR:
                return "\tori\t" + dest + "," + lhs + "," + imm;
            case AND:
                return "\tandi\t" + dest + "," + lhs + "," + imm;
            case XOR:
                return "\txori\t" + dest + "," + lhs + "," + imm;
            case SLL:
                return "\tslli\t" + dest + "," + lhs + "," + imm;
            case SRA:
                return "\tsrai\t" + dest + "," + lhs + "," + imm;
            case SRL:
                return "\tsrli\t" + dest + "," + lhs + "," + imm;
            default:
                assert false;
                return null;
        }
    }
}
