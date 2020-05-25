package ir.maps;

import ast.ASTNode;
import ir.Block;

import java.util.LinkedHashMap;
import java.util.Map;

public class LoopMap {
    private Map<ASTNode, Block> mapOfBody = new LinkedHashMap<>();
    private Map<ASTNode, Block> mapOfEnd = new LinkedHashMap<>();

    public void put(ASTNode node, Block body, Block end) {
        mapOfBody.put(node, body);
        mapOfEnd.put(node, end);
    }

    public Block getBody(ASTNode node){
        return mapOfBody.get(node);
    }

    public Block getEnd(ASTNode node) {
        return mapOfEnd.get(node);
    }
}
