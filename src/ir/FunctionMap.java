package ir;

import scope.DefinedFunction;

import java.util.LinkedHashMap;
import java.util.Map;

public class FunctionMap {
    private Map<DefinedFunction, Function> map = new LinkedHashMap<>();

    public void put(DefinedFunction entity, Function irFunction) {
        map.put(entity, irFunction);
    }

    public Function get(DefinedFunction entity) {
        return map.get(entity);
    }
}
