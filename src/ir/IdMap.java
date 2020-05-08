package ir;

import scope.Entity;

import java.util.LinkedHashMap;
import java.util.Map;

public class IdMap {
    private Map<Entity, Item> map = new LinkedHashMap<>();

    public void put(Entity entity, Item item) {
        map.put(entity, item);
    }

    public Item get(Entity entity) {
        return map.get(entity);
    }
}
