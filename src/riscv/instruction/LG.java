package riscv.instruction;

import riscv.RVGlobal;
import riscv.register.REGISTER;

public class LG extends Instruction {
    REGISTER reg;
    RVGlobal global;

    @Override
    public String toString() {
        return "\tlw\t" + reg + ", " + global;
    }
}
