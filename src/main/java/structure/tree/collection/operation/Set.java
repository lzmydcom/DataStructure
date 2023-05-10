package structure.tree.collection.operation;

import structure.tree.operation.Visitor;

public interface Set<K> {
    int size();
    boolean isEmpty();
    void clear();
    void add(K key);
    void remove(K key);
    boolean contains(K key);
    void traversal(Visitor<K> visitor);

}
