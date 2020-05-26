package riscv.instruction;

import riscv.RVFunction;
import riscv.register.REGISTER;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

public class Call extends Instruction {
    private String symbol;

    public Call(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public void resolve(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr, RVFunction function) {
        
    }

    @Override
    public String toString() {
        return "\tcall\t" + symbol;
    }
}
