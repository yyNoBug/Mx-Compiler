package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

public class Calc extends Instruction {
    private OpClass.Op op;
    private REGISTER dest, lhs, rhs;

    public Calc(OpClass.Op op, REGISTER dest, REGISTER lhs, REGISTER rhs) {
        this.op = op;
        this.dest = dest;
        this.lhs = lhs;
        this.rhs = rhs;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        lhs = super.resolveSrc(virtualMap, itr, lhs, 3);
        rhs = super.resolveSrc(virtualMap, itr, rhs, 4);
        dest = super.resolveDest(virtualMap, itr, dest, function,5);
    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>(){{add(dest);}};
    }

    @Override
    public HashSet<REGISTER> getUses() {
        return new HashSet<>(){{add(lhs); add(rhs);}};
    }

    @Override
    public void replaceUse(REGISTER old, REGISTER newReg) {
        if (lhs == old) lhs = newReg;
        if (rhs == old) rhs = newReg;
    }

    @Override
    public void replaceRd(REGISTER old, REGISTER newReg) {
        if (dest == old) dest = newReg;
    }

    @Override
    public String toString() {
        switch (op) {
            case ADD:
                return "\tadd\t" + dest + "," + lhs + "," + rhs;
            case SUB:
                return "\tsub\t" + dest + "," + lhs + "," + rhs;
            case MUL:
                return "\tmul\t" + dest + "," + lhs + "," + rhs;
            case DIV:
                return "\tdiv\t" + dest + "," + lhs + "," + rhs;
            case REM:
                return "\trem\t" + dest + "," + lhs + "," + rhs;
            case OR:
                return "\tor\t" + dest + "," + lhs + "," + rhs;
            case AND:
                return "\tand\t" + dest + "," + lhs + "," + rhs;
            case XOR:
                return "\txor\t" + dest + "," + lhs + "," + rhs;
            case SLL:
                return "\tsll\t" + dest + "," + lhs + "," + rhs;
            case SRA:
                return "\tsra\t" + dest + "," + lhs + "," + rhs;
            case SRL:
                return "\tsrl\t" + dest + "," + lhs + "," + rhs;
            default:
                assert false;
                return null;
        }
    }
}
