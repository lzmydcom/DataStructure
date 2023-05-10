package structure.tree.trie.operation;

public interface Trie<V> {
    int size();
    boolean isEmpty();
    void clear();

    /**
     * @param str 字符串
     * @return 是否存存储了某个字符串
     */
    boolean contains(String str);
    void add(String str, V value);
    void remove(String str);

    V get(String key);

    /**
     *
     * @param prefix 前缀
     * @return 判断是否存在以某个前缀开头的字符串
     */
    boolean starsWith(String prefix);
}
