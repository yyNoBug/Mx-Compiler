package riscv.instruction;

public class Call extends Instruction {
    private String symbol;

    public Call(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return "\tcall\t" + symbol;
    }
}
