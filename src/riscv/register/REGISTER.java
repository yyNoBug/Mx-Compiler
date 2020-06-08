package riscv.register;

import riscv.instruction.MV;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;

abstract public class REGISTER {
    static public ZERO zero = new ZERO();
    static public SP sp = new SP();
    static public RA ra = new RA();
    static public ARG[] args = new ARG[8];
    static public SAVED[] saveds = new SAVED[12];
    static public TEMP[] temps = new TEMP[7];
    static public List<REGISTER> calleeSavedRegs = new ArrayList<>();
    static public List<REGISTER> callerSavedRegs = new ArrayList<>();
    //public List<REGISTER> argumentRegs = new ArrayList<>();
    static public List<REGISTER> allocatableRegs = new ArrayList<>();
    static public List<REGISTER> physicalRegs = new ArrayList<>();

    static public void initialize() {
        physicalRegs.add(zero);
        physicalRegs.add(sp);
        physicalRegs.add(ra);
        callerSavedRegs.add(ra);
        allocatableRegs.add(ra);
        for (int i = 0; i < 8; ++i) {
            args[i] = new ARG(i);
            callerSavedRegs.add(args[i]);
            allocatableRegs.add(args[i]);
            physicalRegs.add(args[i]);
        }
        for (int i = 0; i < 12; ++i) {
            saveds[i] = new SAVED(i);
            calleeSavedRegs.add(saveds[i]);
            allocatableRegs.add(saveds[i]);
            physicalRegs.add(saveds[i]);
        }
        for (int i = 0; i < 7; ++i) {
            temps[i] = new TEMP(i);
            callerSavedRegs.add(temps[i]);
            allocatableRegs.add(temps[i]);
            physicalRegs.add(temps[i]);
        }
    }

    public int degree = 0;
    public double weight = 0;
    public REGISTER color = null;
    public REGISTER alias = null;
    public HashSet<REGISTER> adjList = new LinkedHashSet<>();
    public HashSet<MV> moveList = new LinkedHashSet<>();

    public void reset() {
        degree = 0;
        weight = 0;
        color = null;
        alias = null;
        adjList.clear();
        moveList.clear();
    }

    public void setPreColored() {
        degree = 99999999;
        weight = 0;
        color = this;
        alias = null;
        adjList.clear();
        moveList.clear();
    }

    public void addAdj(REGISTER reg) {
        adjList.add(reg);
        degree++;
    }
}
