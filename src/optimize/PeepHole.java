package optimize;

import riscv.RVBlock;
import riscv.RVFunction;
import riscv.RVTop;
import riscv.instruction.*;
import riscv.register.REGISTER;

import java.util.HashMap;
import java.util.Map;

public class PeepHole {
    private RVTop rvTop;

    public PeepHole(RVTop rvTop) {
        this.rvTop = rvTop;
        rvTop.getFunctions().forEach(this::deal);
    }

    private void removeUnnecessaryMoves(RVFunction function) {
        for (RVBlock block : function.getBlocks()) {
            for (var itr = block.getInstructions().listIterator(); itr.hasNext();) {
                var inst = itr.next();
                if (inst instanceof MV) {
                    if (((MV) inst).getDest().equals(((MV) inst).getSrc()))
                        itr.remove();
                } else if (inst instanceof CalcI) {
                    var op = ((CalcI) inst).getOp();
                    if ((op == OpClass.Op.ADD
                            || op == OpClass.Op.OR
                            || op == OpClass.Op.XOR
                            || op == OpClass.Op.SLL
                            || op == OpClass.Op.SRA)
                        && ((CalcI) inst).getDest().equals(((CalcI) inst).getLhs())
                        && ((CalcI) inst).getImm() == 0
                    ) {
                        itr.remove();
                    }
                } else if (inst instanceof Calc) {
                    var op = ((Calc) inst).getOp();
                    if ((op == OpClass.Op.ADD
                            || op == OpClass.Op.OR
                            || op == OpClass.Op.XOR
                            || op == OpClass.Op.SLL
                            || op == OpClass.Op.SRA)
                            && ((Calc) inst).getDest().equals(((Calc) inst).getLhs())
                            && ((Calc) inst).getRhs().equals(REGISTER.zero)
                    ) {
                        itr.remove();
                    }
                } else if (inst instanceof SPGrow) {
                    if (((SPGrow) inst).getFunction().getSize() == 0)
                        itr.remove();
                } else if (inst instanceof SPRecover) {
                    if (((SPRecover) inst).getFunction().getSize() == 0)
                        itr.remove();
                }
            }
        }
    }

    private void combineBlocks(RVFunction function){
        boolean flag;
        do {
            flag = false;

            for (RVBlock block : function.getBlocks()) {
                if (block.getInstructions().isEmpty()) {
                    continue;
                }
                var terminalStmt = block.getInstructions().get(block.getInstructions().size() - 1);
                if (terminalStmt instanceof J
                        && (((J) terminalStmt).getDest().precursors.size() == 1
                        && !((J) terminalStmt).getDest().equals(block))) {
                    block.getInstructions().remove(block.getInstructions().size() - 1);
                    RVBlock nextBlk = ((J) terminalStmt).getDest();
                    block.successors.clear();
                    nextBlk.precursors.clear();
                    for (Instruction inst : nextBlk.getInstructions()) {
                        block.add(inst);
                    }
                    nextBlk.successors.forEach(x -> {
                        x.precursors.remove(nextBlk);
                        x.precursors.add(block);
                        block.successors.add(x);
                    });
                    function.getBlocks().remove(nextBlk);
                    flag = true;
                    break;
                }
            }
        } while (flag);
    }

    private void removeRedundantLoad(RVFunction function) {
        for (RVBlock block : function.getBlocks()) {
            Map<REGISTER, Instruction> content = new HashMap<>();

            //System.err.println();
            for (var itr = block.getInstructions().listIterator(); itr.hasNext();) {
                var inst = itr.next();
                //System.err.println(inst);
                boolean modified = false;
                if (inst instanceof LA) {
                    var lastInst = content.get((((LA) inst).getReg()));
                    if (lastInst != null) {
                        if (lastInst instanceof LA && ((LA) lastInst).getStr().equals(((LA) inst).getStr())) {
                            itr.remove();
                            modified = true;
                        }
                    }
                } else if (inst instanceof LI) {
                    var lastInst = content.get(((LI) inst).getReg());
                    if (lastInst != null) {
                        if (lastInst instanceof LI && ((LI) lastInst).getImm() == ((LI) inst).getImm()) {
                            itr.remove();
                            modified = true;
                        }
                    }
                } else if (inst instanceof MV && ((MV) inst).getSrc().equals(REGISTER.zero)) {
                    var lastInst = content.get(((MV) inst).getDest());
                    if (lastInst != null) {
                        if (lastInst instanceof MV && ((MV) lastInst).getSrc().equals(REGISTER.zero)) {
                            itr.remove();
                            modified = true;
                        }
                    }
                }

                itr.previous();
                Instruction prevInst = null;
                if (itr.hasPrevious()) prevInst = itr.previous();
                while (!modified
                        && prevInst != null
                        && !(prevInst instanceof SG)
                        && prevInst.getDefs().size() == 1
                        && inst.getDefs().size() == 1
                        && prevInst.getDefs().iterator().next() == inst.getDefs().iterator().next()) {

                    boolean conflict = false;
                    for (REGISTER i : inst.getUses()) {
                        if (i == inst.getDefs().iterator().next()) {
                            conflict = true;
                            break;
                        }
                    }
                    if (!conflict) {
                        itr.remove();
                        if (itr.hasPrevious()) prevInst = itr.previous();
                        else prevInst = null;
                    } else {
                        break;
                    }
                }
                if (prevInst != null) itr.next();
                itr.next();

                /*
                itr.previous();
                prevInst = null;
                if (itr.hasPrevious()) prevInst = itr.previous();
                while (!modified
                        && prevInst instanceof LI
                        && inst instanceof Calc
                        && inst.getDefs().contains(((LI) prevInst).getReg())
                        && ((LI) prevInst).getImm() < 2048
                        && ((LI) prevInst).getImm() >= -2048) {

                    itr.remove();
                    itr.next();
                    itr.remove();

                    itr.add(new CalcI(((Calc) inst).getOp(), ((Calc) inst).getDest(), ));
                    itr.previous();
                    if (itr.hasPrevious()) prevInst = itr.previous();
                    else prevInst = null;
                }
                if (prevInst != null) itr.next();
                itr.next();
                */

                if (!modified) {
                    for (REGISTER def : inst.getDefs()) {
                        content.put(def, inst);
                    }
                }
            }
        }
    }

    private void removeUnnecessaryJmp (RVFunction function){

        for (var itr = function.getBlocks().listIterator(); itr.hasNext(); ) {
            RVBlock block = itr.next();

            if (block.getInstructions().isEmpty() || !itr.hasNext()) {
                continue;
            }
            RVBlock nextBlock = itr.next();
            itr.previous();
            var terminalStmt = block.getInstructions().get(block.getInstructions().size() - 1);
            if (terminalStmt instanceof J && ((J) terminalStmt).getDest() == nextBlock) {
                block.getInstructions().remove(block.getInstructions().size() - 1);
            }
        }

    }

    private void deal(RVFunction function) {
        removeUnnecessaryMoves(function);
        combineBlocks(function);
        removeRedundantLoad(function);
        removeUnnecessaryJmp(function);
    }
}
