package optimize;

import ir.Block;
import ir.Function;

import java.util.*;

public class DominatorTree {
    static class Node {
        Block block;
        Node IDom;
        int order;

        List<Node> children = new ArrayList<>();
        List<Block> DF = new ArrayList<>();

        Node(Block block, int order) {
            this.block = block;
            this.order = order;
        }
    }

    private Function function;

    private Node root;
    private Map<Block, Node> nodeMap = new HashMap<>();
    private List<Block> blockOrder = new ArrayList<>();

    private Set<Block> visitedBlocks = new HashSet<>();
    private void dfs(Block block) {
        visitedBlocks.add(block);
        blockOrder.add(block);
        for (Block successor : block.successors) {
            if (!visitedBlocks.contains(successor))
                dfs(successor);
        }
    }

    public DominatorTree(Function function) {
        this.function = function;
        deal();
    }

    private Node findLCA(Node u, Node v) {
        while (u != v) {
            if (u.order > v.order) u = u.IDom;
            else v = v.IDom;
        }
        return u;
    }

    private void build() {
        root.IDom = root;

        boolean changed = true;
        while (changed) {
            changed = false;
            for (Block block : blockOrder) {
                Node curNode = nodeMap.get(block);
                if (curNode == root) continue;
                Node leastCommonAncestor = null;
                for (Block precursor : block.precursors) {
                    Node preNode = nodeMap.get(precursor);
                    if (preNode == null) {
                        // block.precursors.remove(precursor);
                        continue;
                    }
                    if (preNode.IDom == null) continue;
                    if (leastCommonAncestor == null) leastCommonAncestor = preNode;
                    else leastCommonAncestor = findLCA(leastCommonAncestor, preNode);
                }
                if (leastCommonAncestor != curNode.IDom) {
                    curNode.IDom = leastCommonAncestor;
                    changed = true;
                }
            }
        }

        nodeMap.values().forEach(node -> {
            if (node != root) {
                node.IDom.children.add(node);
            }
        });
    }

    private void findDF() {
        for (Block block : blockOrder) {
            Set<Block> precursors = block.precursors;
            if (precursors.size() > 1) {
                for (Block precursor : precursors) {
                    Node now = nodeMap.get(precursor);
                    if (now == null) continue;
                    while (now != nodeMap.get(block).IDom) {
                        now.DF.add(block);
                        now = now.IDom;
                    }
                }
            }
        }
    }

    private void deal() {
        var rootBlock = function.getBlockList().get(0);
        dfs(rootBlock);
        //Collections.reverse(blockOrder);

        for (int i = 0; i < blockOrder.size(); ++i) {
            Block block = blockOrder.get(i);
            nodeMap.put(block, new Node(block, i));
        }
        root = nodeMap.get(rootBlock);

        build();
        findDF();
    }

    public List<Block> getDF(Block block) {
        return nodeMap.get(block).DF;
    }

    public List<Block> getBlockOrder() {
        return blockOrder;
    }

    public Map<Block, Node> getNodeMap() {
        return nodeMap;
    }

    public Node getRoot() {
        return root;
    }


}
