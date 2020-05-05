package ir;

import ast.ExprNode;
import scope.DefinedVariable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

public class GlobalMap {
    private ArrayList<DefinedVariable> globalList = new ArrayList<>();
    private Map<DefinedVariable, ExprNode> nodeMap = new LinkedHashMap<>();

    public void add(DefinedVariable definedVariable, ExprNode node){
        globalList.add(definedVariable);
        nodeMap.put(definedVariable, node);
    }

    public ArrayList<DefinedVariable> getGlobalList() {
        return globalList;
    }

    public Map<DefinedVariable, ExprNode> getNodeMap() {
        return nodeMap;
    }
}
