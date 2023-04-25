package structure.treeStructure.collection.operation;

import structure.treeStructure.operation.Visitor;

public interface Set<K> {
    int size();
    boolean isEmpty();
    void clear();
    void add(K key);
    void remove(K key);
    boolean contains(K key);
    void traversal(Visitor<K> visitor);

}
