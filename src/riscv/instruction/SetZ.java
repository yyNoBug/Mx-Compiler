package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class SetZ extends Instruction {
    private OpClass.Cmp op;
    private REGISTER dest;
    private REGISTER cond;

    public SetZ(OpClass.Cmp op, REGISTER dest, REGISTER cond) {
        this.op = op;
        this.dest = dest;
        this.cond = cond;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                        RVFunction function) {
        cond = super.resolveSrc(virtualMap, itr, cond, 3);
        dest = super.resolveDest(virtualMap, itr, dest, function,5);
    }

    @Override
    public String toString() {
        switch (op) {
            case EQ:
                return "\tseqz\t" + dest + "," + cond;
            case NEQ:
                return "\tsnez\t" + dest + "," + cond;
            case GT:
                return "\tsgtz\t" + dest + "," + cond;
            case LT:
                return "\tsltz\t" + dest + "," + cond;
            default:
                assert false;
                return "";
        }
    }
}
