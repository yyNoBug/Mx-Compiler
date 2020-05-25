package riscv.instruction;

import riscv.RVFunction;

public class SPRecover extends Instruction {
    private RVFunction function;

    public SPRecover(RVFunction function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "\taddi\t" + "RVRegister.SP" + "RVRegister.SP" + function.getSize();
    }
}
