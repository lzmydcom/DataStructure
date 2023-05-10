package structure.tree.collection.operation;

public abstract class Visitor<K, V> {
    public static boolean stop = false;
    public abstract boolean visit(K key, V value);
}