package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.HashSet;
import java.util.ListIterator;
import java.util.Map;

import static java.lang.Integer.min;

public class Call extends Instruction {
    private String symbol;
    private int paraSize;

    public Call(String symbol, int paraSize) {
        this.symbol = symbol;
        this.paraSize = paraSize;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {

    }

    @Override
    public HashSet<REGISTER> getDefs() {
        return new HashSet<>(){{
            addAll(REGISTER.callerSavedRegs);
        }};
    }

    @Override
    public HashSet<REGISTER> getUses() {
        return new HashSet<>() {{
            for (int i = 0; i < min(paraSize, 8); ++i) {
                add(REGISTER.args[i]);
            }
        }};
    }

    @Override
    public String toString() {
        return "\tcall\t" + symbol;
    }
}
