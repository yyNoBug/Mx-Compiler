package riscv.register;

abstract public class REGISTER {
    static public ZERO zero = new ZERO();
    static public SP sp = new SP();
    static public RA ra = new RA();
    static public ARG[] args = new ARG[8];
    static public SAVED[] saveds = new SAVED[12];
    static public TEMP[] temps = new TEMP[7];

    static public void initialize() {
        for (int i = 0; i < 8; ++i) args[i] = new ARG(i);
        for (int i = 0; i < 12; ++i) saveds[i] = new SAVED(i);
        for (int i = 0; i < 7; ++i) temps[i] = new TEMP(i);
    }
}
