package backend;

import riscv.RVBlock;
import riscv.RVFunction;
import riscv.RVTop;
import riscv.instruction.*;
import riscv.register.REGISTER;
import riscv.register.SPILLED;
import riscv.register.VIRTUAL;

import java.util.*;

import static java.lang.Integer.min;

public class RegisterAllocator {
    private RVTop top;
    private Map<VIRTUAL, REGISTER> virtualMap = new LinkedHashMap<>();
    private RVFunction curFunction;

    static class Edge {
        public REGISTER u, v;
        public Edge(REGISTER u, REGISTER v) {
            this.u = u;
            this.v = v;
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof Edge && ((Edge) obj).u.equals(u) && ((Edge) obj).v.equals(v);
        }

        @Override
        public int hashCode() {
            return u.hashCode() ^ v.hashCode();
        }
    }

    private void addEdge(REGISTER u, REGISTER v) {
        //System.err.println(u + " " + v);
        if (u != v && !adjSet.contains(new Edge(u, v))) {
            adjSet.add(new Edge(u, v));
            adjSet.add(new Edge(v, u));
            if (!preColored.contains(u)) {
                u.addAdj(v);
            }
            if (!preColored.contains(v)) {
                v.addAdj(u);
            }
        }
    }


    int K;

    HashSet<REGISTER> preColored = new LinkedHashSet<>();
    HashSet<REGISTER> initial = new LinkedHashSet<>();
    HashSet<REGISTER> simplifyWorkList = new LinkedHashSet<>();
    HashSet<REGISTER> freezeWorkList = new LinkedHashSet<>();
    HashSet<REGISTER> spillWorkList = new LinkedHashSet<>();
    HashSet<REGISTER> spilledNodes = new LinkedHashSet<>();
    HashSet<REGISTER> coalescedNodes = new LinkedHashSet<>();
    HashSet<REGISTER> coloredNodes = new LinkedHashSet<>();
    HashSet<Edge> adjSet = new LinkedHashSet<>();
    Stack<REGISTER> selectStack = new Stack<>();

    HashSet<MV> coalescedMoves = new LinkedHashSet<>();
    HashSet<MV> constrainedMoves = new LinkedHashSet<>();
    HashSet<MV> frozenMoves = new LinkedHashSet<>();
    HashSet<MV> workListMoves = new LinkedHashSet<>();
    HashSet<MV> activeMoves = new LinkedHashSet<>();

    HashSet<REGISTER> spillTemps = new LinkedHashSet<>();


    public RegisterAllocator(RVTop top) {
        this.top = top;
        K = REGISTER.allocatableRegs.size();
        preColored.addAll(REGISTER.physicalRegs);
        for (REGISTER u : preColored) {
            for (REGISTER v : preColored) {
                if (u != v)
                    addEdge(u, v);
            }
        }
    }

    public void allocate() {
        var functions = top.getFunctions();
        for (RVFunction function : functions) {
            visit(function);
        }
    }

    private void init() {
        initial.clear();
        simplifyWorkList.clear();
        freezeWorkList.clear();
        spillWorkList.clear();
        spilledNodes.clear();
        coalescedNodes.clear();
        coloredNodes.clear();
        selectStack.clear();

        coalescedMoves.clear();
        constrainedMoves.clear();
        frozenMoves.clear();
        workListMoves.clear();
        activeMoves.clear();

        adjSet.clear();
        curFunction.getBlocks().forEach(block -> {
            for (Instruction inst : block.getInstructions()) {
                initial.addAll(inst.getDefs());
                initial.addAll(inst.getUses());
            }
        });
        initial.removeAll(preColored);
        initial.forEach(REGISTER::reset);
        preColored.forEach(REGISTER::setPreColored);

        for (RVBlock block : curFunction.getBlocks()) {
            double weight = Math.pow(10, min(block.successors.size(), block.precursors.size()));
            for (Instruction inst : block.getInstructions()) {
                inst.getUses().forEach(reg -> reg.weight += weight);
                if (inst.getDefs().size() > 0) {
                    inst.getDefs().iterator().next().weight += weight;
                }
            }
        }
    }

    private void build() {
        for (RVBlock block : curFunction.getBlocks()) {
            HashSet<REGISTER> currentLive = new LinkedHashSet<>(block.liveOut);
            // System.err.println("===" + block + currentLive); // TODO
            for (int i = block.getInstructions().size() - 1; i >= 0; --i) {
                var inst = block.getInstructions().get(i);
                if (inst instanceof MV) {
                    currentLive.removeAll(inst.getUses());
                    HashSet<REGISTER> mvAbout = inst.getUses();
                    mvAbout.addAll(inst.getDefs());
                    for (REGISTER reg : mvAbout) {
                        reg.moveList.add((MV) inst);
                    }
                    workListMoves.add((MV) inst);
                }
                if (inst instanceof SG) {
                    addEdge(((SG) inst).getRd(), ((SG) inst).getRt());
                }
                HashSet<REGISTER> defs = inst.getDefs();
                // currentLive.add(REGISTER.zero); // ????
                currentLive.addAll(defs);
                // System.err.println(currentLive); // TODO
                for (REGISTER def : defs) {
                    for (REGISTER reg : currentLive) {
                        addEdge(reg, def);
                        // System.err.println(reg + " " + def); // TODO
                    }
                }
                currentLive.removeAll(defs);
                currentLive.addAll(inst.getUses());
            }
        }
    }

    private void makeWorklist() {
        for (REGISTER node : initial) {
            if (node.degree >= K) {
                spillWorkList.add(node);
            }
            else if (moveRelated(node)) {
                freezeWorkList.add(node);
            }
            else {
                if (REGISTER.physicalRegs.contains(node))
                    throw new RuntimeException();
                simplifyWorkList.add(node);
            }
        }
    }

    private HashSet<REGISTER> adjacent(REGISTER n) {
        return new LinkedHashSet<>(n.adjList) {{
            removeAll(selectStack);
            removeAll(coalescedNodes);
        }};
    }

    private HashSet<REGISTER> adjacent(REGISTER u, REGISTER v) {
        var ret = adjacent(u);
        ret.addAll(adjacent(v));
        return ret;
    }

    private HashSet<MV> nodeMoves(REGISTER n) {
        return new LinkedHashSet<>(workListMoves) {{
            addAll(activeMoves);
            retainAll(n.moveList);
        }};
    }

    private boolean moveRelated(REGISTER n) {
        return !nodeMoves(n).isEmpty();
    }

    private void simplify() {
        REGISTER reg = simplifyWorkList.iterator().next();
        simplifyWorkList.remove(reg);
        selectStack.push(reg);
        for (REGISTER reg1 : adjacent(reg)) {
            decrementDegree(reg1);
        }
    }

    private void decrementDegree(REGISTER reg) {
        if (reg.degree-- == K) {
            HashSet<REGISTER> nodes = new LinkedHashSet<>(adjacent(reg));
            nodes.add(reg);
            enableMoves(nodes);
            spillWorkList.remove(reg);
            if (moveRelated(reg)) freezeWorkList.add(reg);
            else {
                if (REGISTER.physicalRegs.contains(reg))
                    throw new RuntimeException();
                simplifyWorkList.add(reg);
            }
        }
    }

    private void enableMoves(HashSet<REGISTER> nodes) {
        //if nothing in active moves, it is not needed
        nodes.forEach(node -> nodeMoves(node).forEach(move -> {
            if (activeMoves.contains(move)) {
                activeMoves.remove(move);
                workListMoves.add(move);
            }
        }));
    }

    private void coalesce() {
        MV move = workListMoves.iterator().next();
        REGISTER x = getAlias(move.getDest());
        REGISTER y = getAlias(move.getSrc());
        REGISTER u, v;
        if (preColored.contains(y)) {
            u = y;
            v = x;
        } else {
            u = x;
            v = y;
        }
        workListMoves.remove(move);
        if (u == v) {
            coalescedMoves.add(move);
            addWorkList(u);
        } else if (preColored.contains(v) || adjSet.contains(new Edge(u, v))) {
            constrainedMoves.add(move);
            addWorkList(u);
            addWorkList(v);
        } else {
            if ((preColored.contains(u) && judge(u, v)) ||
                    (!preColored.contains(u) && conservative(adjacent(u, v))) ) {
                coalescedMoves.add(move);
                combine(u, v);
                addWorkList(u);
            } else {
                activeMoves.add(move);
            }
        }
    }

    private void addWorkList(REGISTER u) {
        if (!preColored.contains(u) && !moveRelated(u) && u.degree < K) {
            freezeWorkList.remove(u);
            if (REGISTER.physicalRegs.contains(u))
                throw new RuntimeException();
            simplifyWorkList.add(u);
        }
    }

    private boolean OK(REGISTER t, REGISTER r) {
        return t.degree < K || preColored.contains(t) || adjSet.contains(new Edge(t, r));
    }

    private boolean judge(REGISTER u, REGISTER v) {
        for (REGISTER t : adjacent(v)) {
            if (!OK(t, u))
                return false;
        }
        return true;
    }

    private boolean conservative(HashSet<REGISTER> nodes) {
        int k = 0;
        for (REGISTER n : nodes) {
            if (n.degree >= K) k++;
        }
        return k < K;
    }

    private REGISTER getAlias(REGISTER n) {
        if (coalescedNodes.contains(n)) {
            n.alias = getAlias(n.alias);
            return n.alias;
        }
        else return n;
    }

    private void combine(REGISTER u, REGISTER v) {
        if (!freezeWorkList.remove(v))
            spillWorkList.remove(v);
        coalescedNodes.add(v);
        v.alias = u;
        u.moveList.addAll(v.moveList);
        enableMoves(new LinkedHashSet<>() {{add(v);}});
        for (REGISTER t : adjacent(v)) {
            addEdge(t, u);
            decrementDegree(t);
        }
        if (u.degree >= K && freezeWorkList.contains(u)) {
            freezeWorkList.remove(u);
            spillWorkList.add(u);
        }
    }

    private void freeze() {
        REGISTER u = freezeWorkList.iterator().next();
        freezeWorkList.remove(u);
        if (REGISTER.physicalRegs.contains(u))
            throw new RuntimeException();
        simplifyWorkList.add(u);
        freezeMoves(u);
    }

    private void freezeMoves(REGISTER u) {
        for (MV m : nodeMoves(u)) {
            var x = m.getDest();
            var y = m.getSrc();
            REGISTER v;
            if (getAlias(y) == getAlias(u)) v = getAlias(x);
            else v = getAlias(y);
            activeMoves.remove(m);
            frozenMoves.add(m);
            if (nodeMoves(v).isEmpty() && v.degree < K) {
                freezeWorkList.remove(v);
                if (REGISTER.physicalRegs.contains(v))
                    throw new RuntimeException();
                simplifyWorkList.add(v);
            }
        }
    }

    private void selectSpill() {
        REGISTER m = null;
        double min = 1e9;
        boolean flag = false;

        for (REGISTER reg : spillWorkList) {
            double cost = reg.weight / reg.degree;
            if (!spillTemps.contains(reg)) {
                flag = true;
                if (cost < min) {
                    min = cost;
                    m = reg;
                }
            } else if (!flag) {
                m = reg;
            }
        }
        spillWorkList.remove(m);
        if (REGISTER.physicalRegs.contains(m))
            throw new RuntimeException();
        simplifyWorkList.add(m);
        freezeMoves(m);
    }

    private void assignColors() {
        while (!selectStack.isEmpty()) {
            REGISTER n = selectStack.pop();
            if (REGISTER.physicalRegs.contains(n))
                throw new RuntimeException();
            ArrayList<REGISTER> okColors = new ArrayList<>(REGISTER.allocatableRegs);
            HashSet<REGISTER> colored = new LinkedHashSet<>(coloredNodes);
            colored.addAll(preColored);
            for (REGISTER w : n.adjList) {
                if (colored.contains(getAlias(w))) {
                    okColors.remove(getAlias(w).color);
                }
            }
            if (okColors.isEmpty()) {
                spilledNodes.add(n);
            }
            else {
                coloredNodes.add(n);
                n.color = okColors.get(0);
            }
        }
        coalescedNodes.forEach(n -> n.color = getAlias(n).color);
    }

    //int spilledCount = 0;
    private void rewriteProgram() {
        HashSet<REGISTER> newTemps = new LinkedHashSet<>();
        //spilledCount += spilledNodes.size();
        spilledNodes.forEach(n -> {
            n.color = new SPILLED(curFunction);
        });
        for (RVBlock block : curFunction.getBlocks()) {
            for (Instruction inst: block.getInstructions()) {
                if (inst.getDefs().size() == 1) {
                    REGISTER dest = inst.getDefs().iterator().next();
                    if (dest instanceof VIRTUAL) {
                        getAlias(dest);
                    }
                }
            }
        }

        for (RVBlock block : curFunction.getBlocks()) {
            var instList = block.getInstructions();
            for (var itr = instList.listIterator(0); itr.hasNext();) {
                var inst = itr.next();
                //inst.resolve(virtualMap, itr, curFunction);

                for (REGISTER reg : inst.getUses()) {
                    if (reg.color instanceof SPILLED) {
                        if (inst.getDefs().contains(reg)) {
                            VIRTUAL tmp = new VIRTUAL();
                            itr.previous();
                            itr.add(new LOAD(tmp, ((SPILLED) reg.color).getAddr()));
                            itr.next();
                            itr.add(new STORE(tmp, ((SPILLED) reg.color).getAddr()));
                            inst.replaceUse(reg, tmp);
                            inst.replaceRd(reg, tmp);
                            newTemps.add(tmp);

                        }
                        else {
                            if (inst instanceof MV && ((MV)inst).getSrc() == reg &&
                                    (!(((MV) inst).getDest().color instanceof SPILLED))) {
                                Instruction replace;
                                /*if (reg instanceof VIRTUAL && ((VIRTUAL) reg).isImm) {
                                    replace = new LI(((VIRTUAL) reg).imm, ((MV) inst).getDest(), block);
                                } else*/ {
                                    replace = new LOAD(((MV) inst).getDest(), ((SPILLED) reg.color).getAddr());
                                }
                                itr.set(replace);
                                inst = replace;
                            } else {
                                VIRTUAL tmp = new VIRTUAL();
                                /*if (reg instanceof VIRTUAL && ((VIRTUAL) reg).isImm) {
                                    inst.insertBefore(new LI(((VIRTUAL) reg).imm, tmp, block));
                                } else*/ {
                                    itr.previous();
                                    itr.add(new LOAD(tmp, ((SPILLED) reg.color).getAddr()));
                                    itr.next();
                                }
                                inst.replaceUse(reg, tmp);
                                newTemps.add(tmp);
                            }
                        }
                    }
                }
                for (REGISTER def : inst.getDefs()) {
                    if (def.color instanceof SPILLED) {
                        if (!inst.getUses().contains(def)) {
                            /*if (def instanceof VIRTUAL && ((VIRTUAL) def).isImm) {
                                RVInst replace = new Mv(root.regMap.get("zero"), root.regMap.get("zero"), block);
                                inst.replace(replace);
                                inst = replace;
                            } else*/ if (inst instanceof MV && !(((MV) inst).getSrc().color instanceof SPILLED)) {
                                Instruction replace = new STORE(((MV) inst).getSrc(), ((SPILLED) def.color).getAddr());
                                itr.set(replace);
                                inst = replace;
                            }
                            else {
                                var tmp = new VIRTUAL();
                                inst.replaceRd(def, tmp);
                                itr.add(new STORE(tmp, ((SPILLED) def.color).getAddr()));
                                newTemps.add(tmp);
                            }
                        }
                    }
                }
            }
        }

        spillTemps.addAll(newTemps);
        initial.addAll(coloredNodes);
        initial.addAll(coalescedNodes);
        initial.addAll(newTemps);
    }

    private void visit(RVFunction function) {
        curFunction = function;
        while (true) {
            init();
            LivenessAnalysis.runForFunction(function);
            build();
            makeWorklist();
            do{
                if (!simplifyWorkList.isEmpty()) {
                    simplify();
                }
                else if (!workListMoves.isEmpty()) {
                    coalesce();
                }
                else if (!freezeWorkList.isEmpty()) {
                    freeze();
                }
                else if (!spillWorkList.isEmpty()) {
                    selectSpill();
                }
            }
            while (!(freezeWorkList.isEmpty() && simplifyWorkList.isEmpty() &&
                    spillWorkList.isEmpty() && workListMoves.isEmpty()));
            assignColors();
            if (spilledNodes.isEmpty()) {
                break;
            }
            rewriteProgram();
        }

        for (RVBlock rvBlock : function.getBlocks()) {
            visit(rvBlock);
        }
    }

    private void visit(RVBlock block) {
        var instList = block.getInstructions();
        for (var itr = instList.listIterator(0); itr.hasNext();) {
            var inst = itr.next();
            inst.resolve(virtualMap, itr, curFunction);
        }
    }
}
