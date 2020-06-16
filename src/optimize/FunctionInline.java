package optimize;

import ir.Block;
import ir.DeclaredFunction;
import ir.IRTop;
import ir.irStmt.*;
import ir.items.Item;
import ir.items.Local;

import java.util.*;

public class FunctionInline {
    static private int limit = 150;

    private class Node{
        DeclaredFunction function;
        Set<Node> next = new HashSet<>();
        Set<Node> prev = new HashSet<>();

        public Node(DeclaredFunction function) {
            this.function = function;
        }

        public int getSize() {
            int size = 0;
            for (Block block : function.getBlockList()) {
                size += block.getStmtList().size();
            }
            return size;
        }
    }

    private class NodeComparator implements Comparator<Node> {
        @Override
        public int compare(Node o1, Node o2) {
            return Integer.compare(o1.getSize(), o2.getSize());
        }
    }

    private IRTop irTop;

    private Map<DeclaredFunction, Node> nodeMap = new HashMap<>();
    private Queue<Node> priorityQueue = new PriorityQueue<>(new NodeComparator());

    public FunctionInline(IRTop irTop) {
        this.irTop = irTop;
        deal();
    }

    private void clear() {
        nodeMap.clear();
        priorityQueue.clear();
    }

    private void deal() {
        clear();
        irTop.getFunctions().forEach(x -> nodeMap.put(x, new Node(x)));
        for (DeclaredFunction function : irTop.getFunctions()) {
            for (Block block : function.getBlockList()) {
                for (Statement statement : block.getStmtList()) {
                    if (statement instanceof CallStmt &&
                            ((CallStmt) statement).getFunction() instanceof DeclaredFunction) {
                        var begin = nodeMap.get(function);
                        var end = nodeMap.get(((DeclaredFunction)((CallStmt) statement).getFunction()));
                        begin.next.add(end);
                        end.prev.add(begin);
                    }
                }
            }
        }
        for (Node node : nodeMap.values()) {
            if (node.next.isEmpty()) priorityQueue.add(node);
        }
        while (!priorityQueue.isEmpty()) {
            var cur = priorityQueue.poll();
            unfoldCallee(cur);
            cur.prev.forEach( x -> {
                x.next.remove(cur);
                if (x.next.isEmpty()) priorityQueue.add(x);
            });
            cur.prev.clear();
        }
        for (Node node : nodeMap.values()) {
            // Unfold recursive function.
            unfoldFunction(node, node);
            unfoldFunction(node, node);
            unfoldFunction(node, node);
            unfoldFunction(node, node);
            unfoldFunction(node, node);
//            unfoldFunction(node, node);
//            unfoldFunction(node, node);
//            unfoldFunction(node, node);
        }
    }

    private void unfoldCallee(Node node) {
        node.prev.forEach(x -> {
            unfoldFunction(x, node);
        });
    }

    private int count = 0;

    private void unfoldFunction(Node a, Node b) {
        int maxTimes = (limit - a.getSize()) / b.getSize();
        int times = 0;
        Map<Block, Block> target = new HashMap<>();
        Map<Block, Block> from = new HashMap<>();
        List<Block> translateBlocks = new ArrayList<>();

        for (Block block : a.function.getBlockList()) {
            var newBlock = new Block(block.getName());
            target.put(block, newBlock);
            translateBlocks.add(newBlock);

            for (Statement statement : block.getStmtList()) {
                if (statement instanceof CallStmt && ((CallStmt) statement).getFunction() == b.function
                        && ++times <= maxTimes) {

                    var itemMap = new HashMap<Item, Item>();

                    int size = ((CallStmt) statement).getParameters().size();
                    for (int j = 0; j < size; ++j) {
                        itemMap.put(b.function.getArgs().get(j), ((CallStmt) statement).getParameters().get(j));
                    }
                    b.function.getBlockList().forEach(x->{
                        x.getStmtList().forEach(y -> {
                            if (y.getDef() != null) itemMap.put(y.getDef(), new Local());
                        });
                    });

                    Block endBlk = new Block("inline." + count + ".end");
                    var newName = new HashMap<Block, Block>();
                    boolean isFirst = true;
                    for (Block x : b.function.getBlockList()) {
                        var blk = new Block("inline." + count + "." + x.getName());
                        newName.put(x, blk);
                        if (isFirst) {
                            translateBlocks.get(translateBlocks.size() - 1).add(new JmpStmt(blk));
                            isFirst = false;
                        }

                        x.getStmtList().forEach(stmt -> {
                            if (stmt instanceof RetStmt) blk.add(new JmpStmt(endBlk));
                            else blk.add(stmt.transform(itemMap));
                        });
                    }
                    for (Block x : b.function.getBlockList()) {
                        var curBlock = newName.get(x);
                        curBlock.translateTarget(newName);
                        translateBlocks.add(curBlock);
                    }

                    translateBlocks.add(endBlk);
                    if (((CallStmt) statement).getResult() != null) {
                        var temp = new HashMap<Block, Item>();
                        for (Block blk : b.function.getBlockList()) {
                            if (blk.peak() instanceof RetStmt) {
                                var item = ((RetStmt) blk.peak()).getItem();
                                if (itemMap.containsKey(item)) item = itemMap.get(item);
                                temp.put(newName.get(blk), item);
                            }
                        }
                        endBlk.add(new PhiStmt(((CallStmt) statement).getResult(), temp));
                    }
                    count++;
                }
                else {
                    translateBlocks.get(translateBlocks.size() - 1).add(statement);
                }

                from.put(block, translateBlocks.get(translateBlocks.size() - 1));
            }
        }

        a.function.getBlockList().clear();
        translateBlocks.forEach(x -> {
            a.function.getBlockList().add(x.translatePhiTarget(from).translateTarget(target));
        });
    }
}
