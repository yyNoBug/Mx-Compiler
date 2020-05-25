package riscv.instruction;

import riscv.RVFunction;

public class SPGrow extends Instruction {
    private RVFunction function;

    public SPGrow(RVFunction function) {
        this.function = function;
    }

    @Override
    public String toString() {
        return "\taddi\t" + "RVRegister.SP" + "RVRegister.SP" + -function.getSize();
    }
}
