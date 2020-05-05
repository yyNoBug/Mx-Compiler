package ir;

import scope.Entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class IdMap {
    private Map<Entity, Register> map = new LinkedHashMap<>();

    public void put(Entity entity, Register reg) {
        map.put(entity, reg);
    }

    public Register get(Entity entity) {
        return map.get(entity);
    }
}
