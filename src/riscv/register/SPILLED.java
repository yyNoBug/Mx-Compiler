package riscv.register;

import riscv.RVFunction;
import riscv.addr.Address;
import riscv.addr.StackAddr;

public class SPILLED extends REGISTER {
    private Address addr;

    public SPILLED(Address addr) {
        this.addr = addr;
    }

    public SPILLED(RVFunction function) {
        addr = new StackAddr(function, function.getTopIndex());
    }

    public Address getAddr() {
        return addr;
    }
}
