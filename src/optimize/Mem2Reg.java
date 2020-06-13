package optimize;

import ir.Block;
import ir.Function;
import ir.IRTop;
import ir.irStmt.*;
import ir.items.Item;
import ir.items.Local;
import org.antlr.v4.runtime.misc.Pair;

import java.util.*;

public class Mem2Reg {

    private int cnt = 0;
    private class Variable {
        int num;

        public Variable() {
            num = cnt++;
        }
    }

    private IRTop irTop;

    private Map<Item, Variable> itemToVariableMap = new HashMap<>();
    private Map<Variable, Set<Block>> assignedVarMap = new HashMap<>();
    private Map<Block, Set<Variable>> varContMap = new HashMap<>();
    private Map<Block, Set<Variable>> varNeedMap = new HashMap<>();
    private Map<Pair<Block, Variable>, Item> phiResultMap = new HashMap<>();
    private Map<Pair<Block, Variable>, Map<Block, Item>> phiToAddMap = new HashMap<>();
    private Map<Variable, LinkedList<Item>> variableToItemsMap = new HashMap<>();
    private Map<Item, Item> aliasMap = new HashMap<>();

    public Mem2Reg(IRTop irTop) {
        this.irTop = irTop;
        deal();
    }

    private void rename(DominatorTree.Node now) {
        List<Variable> trace = new ArrayList<>();

        for (Variable variable : varNeedMap.get(now.block)) {
            trace.add(variable);
            variableToItemsMap.get(variable).add(phiResultMap.get(new Pair<>(now.block, variable)));
        }

        var iterator = now.block.getStmtList().listIterator();
        while (iterator.hasNext()) {
            Statement statement = iterator.next();
            if (statement instanceof AllocateStmt) {
                iterator.remove();

            } else if (statement instanceof LoadStmt) {
                var variable = itemToVariableMap.get(((LoadStmt) statement).getSrc());
                if (variable != null) {
                    iterator.remove();
                    aliasMap.put(((LoadStmt) statement).getDest(), variableToItemsMap.get(variable).peekLast());
                }

            } else if (statement instanceof StoreStmt) {
                var variable = itemToVariableMap.get(((StoreStmt) statement).getDest());
                if (variable != null) {
                    trace.add(variable);
                    variableToItemsMap.get(variable).add(((StoreStmt) statement).getSrc());
                    iterator.remove();
                }
            }
        }

        for (Block block : now.block.successors) {
            for (Variable variable : varNeedMap.get(block)) {
                phiToAddMap.get(new Pair<>(block, variable)).put(now.block, variableToItemsMap.get(variable).peekLast());
            }
        }

        now.children.forEach(this::rename);
        trace.forEach(v -> variableToItemsMap.get(v).removeLast());
    }


    private Item newName(Item item) {
        if (aliasMap.containsKey(item)) return newName(aliasMap.get(item));
        return item;
    }

    private void eliminateAlias(Block blk) {
        blk.getPhiStmts().forEach(x ->
            x.getMap().forEach((b, reg) -> {
                x.getMap().replace(b, newName(reg));
            })
        );

        for (Statement statement : blk.getStmtList()) {
            if (statement instanceof LoadStmt) {
                ((LoadStmt) statement).setSrc(newName(((LoadStmt)statement).getSrc()));
            } else if (statement instanceof StoreStmt) {
                ((StoreStmt) statement).setSrc(newName(((StoreStmt)statement).getSrc()));
                ((StoreStmt) statement).setDest(newName(((StoreStmt)statement).getDest()));
            } else if (statement instanceof OpStmt) {
                ((OpStmt) statement).setOpr1(newName(((OpStmt) statement).getOpr1()));
                ((OpStmt) statement).setOpr2(newName(((OpStmt) statement).getOpr2()));
            } else if (statement instanceof CallStmt) {
                var items = ((CallStmt) statement).getParameters();
                for (var itr = items.listIterator(); itr.hasNext();){
                    itr.set(newName(itr.next()));
                }
            } else if (statement instanceof RetStmt) {
                ((RetStmt) statement).setItem(newName(((RetStmt) statement).getItem()));
            } else if (statement instanceof BranchStmt) {
                ((BranchStmt) statement).setCondition(newName(((BranchStmt) statement).getCondition()));
            }
        }
    }

    private void clear() {
        itemToVariableMap.clear();
        assignedVarMap.clear();
        varContMap.clear();
        varNeedMap.clear();
        phiResultMap.clear();
        phiToAddMap.clear();
        variableToItemsMap.clear();
        aliasMap.clear();
    }

    private void optimizeFunction(Function function) {
        clear();
        DominatorTree dominatorTree = new DominatorTree(function);

        function.getBlockList().removeIf(block -> !dominatorTree.getNodeMap().containsKey(block));

        for (Block block : dominatorTree.getBlockOrder()) {
            for (Statement instruction : block.getStmtList()) {
                if (instruction instanceof AllocateStmt) {
                    Variable temp = new Variable();
                    itemToVariableMap.put(((AllocateStmt) instruction).getItem(), temp);
                    assignedVarMap.put(temp, new HashSet<>());
                }
            }
            varContMap.put(block, new HashSet<>());
            varNeedMap.put(block, new HashSet<>());
        }

        for (Block block : function.getBlockList()) {
            for (Statement instruction : block.getStmtList()) {
                if (instruction instanceof StoreStmt) {
                    Variable temp = itemToVariableMap.get(((StoreStmt) instruction).getDest());
                    if (temp != null) {
                        assignedVarMap.get(temp).add(block);
                        varContMap.get(block).add(temp);
                    }
                }
            }
        }

        assignedVarMap.forEach((variable, sites) -> {
            Queue<Block> queue = new LinkedList<>(sites);
            for (Block now = queue.poll(); now != null; now = queue.poll()) {
                for (Block frontier : dominatorTree.getDF(now)) {
                    if (varNeedMap.get(frontier).contains(variable)) continue;
                    varNeedMap.get(frontier).add(variable);
                    phiResultMap.put(new Pair<>(frontier, variable), new Local());
                    phiToAddMap.put(new Pair<>(frontier, variable), new HashMap<>());
                    if (!sites.contains(frontier)) queue.add(frontier);
                }
            }
        });

        for (Variable value : itemToVariableMap.values()) {
            variableToItemsMap.put(value, new LinkedList<>(){{
                add(new Local());
            }});
        }

        rename(dominatorTree.getRoot());

        phiToAddMap.forEach((key, entry) -> {
            key.a.add(new PhiStmt(((Local) phiResultMap.get(key)), entry));
        });

        dominatorTree.getBlockOrder().forEach(this::eliminateAlias);
    }

    public void deal() {
        irTop.getFunctions().forEach(this::optimizeFunction);
    }
}