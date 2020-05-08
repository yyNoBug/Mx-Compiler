package riscv.register;

abstract public class REGISTER {
    static private ZERO zero = new ZERO();
    static private SP sp = new SP();
    static private RA ra = new RA();
    static private ARG[] args = new ARG[8];
    static private SAVED[] saveds = new SAVED[12];
    static private TEMP[] temps = new TEMP[7];
}
