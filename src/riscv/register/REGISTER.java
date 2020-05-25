package riscv.register;

abstract public class REGISTER {
    static public ZERO zero = new ZERO();
    static public SP sp = new SP();
    static public RA ra = new RA();
    static public ARG[] args = new ARG[8];
    static public SAVED[] saveds = new SAVED[12];
    static public TEMP[] temps = new TEMP[7];
}
