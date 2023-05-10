package structure.tree.collection.set;

import structure.tree.collection.map.HashMap;
import structure.tree.collection.operation.Set;
import structure.tree.operation.Visitor;


/**
 * 使用HashMap实现的HashSet
 * @param <K> Map的Key
 */
public class HashSet<K> implements Set<K> {
    /**
     * 聚合了HashMap
     */
    private final HashMap<K, Object> map;

    public HashSet() {
        this.map = new HashMap<>();
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public void add(K key) {
        map.put(key, null);
    }

    @Override
    public void remove(K key) {
        map.remove(key);
    }

    @Override
    public boolean contains(K key) {
        return map.containsKey(key);
    }

    @Override
    public void traversal(Visitor<K> visitor) {
        map.traversal(new structure.tree.collection.operation.Visitor<K, Object>() {
            @Override
            public boolean visit(K key, Object value) {
                visitor.visit(key);
                return false;
            }
        });
    }
}
