package riscv.instruction;

import riscv.RVFunction;
import riscv.addr.Address;
import riscv.register.REGISTER;
import riscv.register.SPILLED;
import riscv.register.VIRTUAL;

import java.util.ListIterator;
import java.util.Map;

abstract public class Instruction {
    abstract public void resolve(Map<VIRTUAL, REGISTER> virtualMap,
                                 ListIterator<Instruction> itr, RVFunction function);

    protected REGISTER resolveSrc(Map<VIRTUAL, REGISTER> virtualMap,
                              ListIterator<Instruction> itr, REGISTER src, int n) {
        REGISTER ret = src;
        if (ret instanceof VIRTUAL) {
            ret = virtualMap.get(ret);
            if (ret instanceof SPILLED) {
                itr.previous();
                itr.add(new LOAD(REGISTER.temps[n], ((SPILLED) ret).getAddr()));
                itr.next();
                ret = REGISTER.temps[n];
            }
        }
        return ret;
    }

    protected REGISTER resolveDest(Map<VIRTUAL, REGISTER> virtualMap, ListIterator<Instruction> itr,
                                   REGISTER dest, RVFunction function, int n) {
        REGISTER ret = dest;
        if (ret instanceof VIRTUAL) {
            if (virtualMap.containsKey(ret)) {
                ret = virtualMap.get(ret);
                if (ret instanceof SPILLED) {
                    itr.add(new STORE(REGISTER.temps[n], ((SPILLED) ret).getAddr()));
                    ret = REGISTER.temps[n];
                }
            }
            else {
                var spilled = new SPILLED(function);
                virtualMap.put(((VIRTUAL) ret), spilled);
                ret = REGISTER.temps[n];
                itr.add(new STORE(ret, spilled.getAddr()));
            }
        }
        return ret;
    }

    protected void resolveAddr(Map<VIRTUAL, REGISTER> virtualMap,
                                   ListIterator<Instruction> itr, Address addr, int n) {
        REGISTER tmp = addr.getBase();
        if (tmp instanceof VIRTUAL) {
            tmp = virtualMap.get(tmp);
            if (tmp instanceof SPILLED) {
                itr.previous();
                itr.add(new LOAD(REGISTER.temps[n], ((SPILLED) tmp).getAddr()));
                itr.next();
                addr.setBase(REGISTER.temps[n]);
            }
        }
    }
}
