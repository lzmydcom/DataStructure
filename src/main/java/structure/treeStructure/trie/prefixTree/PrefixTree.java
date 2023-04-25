package structure.treeStructure.trie.prefixTree;

import structure.linearStructure.array.stack.ArrayStack;
import structure.treeStructure.collection.map.HashMap;
import structure.treeStructure.trie.operation.Trie;

/**
 * 从一堆不重复字符串中查找是否存在某个字符串前缀适合使用这种数据结构
 *
 * @param <V> 一个完整word中允许存储一个value
 */
public class PrefixTree<V> implements Trie<V> {

    private int size;

    private Node<V> root;

    private static final boolean RED = true;
    private static final boolean BLUE = false;
    private static class Node<V> {
        public Node(V value) {
            this.value = value;
        }

        HashMap<Character, Node<V>> children;
        boolean color = BLUE;
        V value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean contains(String str) {
        Node<V> node = node(str);
        return node != null;
    }

    @Override
    public void add(String str, V value) {
        char[] chars = getChars(str);
        if (root == null) {
            //添加到根节点
            root = new Node<>(null);
            Node<V> node = root;
            for (Character character : chars) {
                node.children = new HashMap<>();
                Node<V> newNode = new Node<>(null);
                node.children.put(character, newNode);
                node = newNode;
            }
            node.color = RED;
            node.value = value;
            return;
        }
        //添加的不是根节点
        //遍历所有children，查找是否已经有char子节点
        Node<V> node = root;

        HashMap<Character, Node<V>> children;
        HashMap<Character, Node<V>> map;
        Node<V> vNode;
        for (Character character : chars) {
            children = node.children;
            vNode = new Node<>(null);
            if (children == null) {
                //没有完整单词，添加完整单词
                map = new HashMap<>();
                map.put(character, vNode);
                node.children = map;
                node = vNode;
                continue;
            }
            node = children.get(character);
            if (node == null) {
                //没有完整单词，添加完整单词
                children.put(character, vNode);
                node = vNode;
            }
        }
        node.value = value;
        node.color = RED;
    }

    @Override
    public void remove(String str) {
        if (root == null) return;
        //有这个单词，进行删除
        Node<V> node = root;
        ArrayStack<HashMap<Character, Node<V>>> stack = new ArrayStack<>();
        char[] chars = getChars(str);
        HashMap<Character, Node<V>> children = node.children;
        for (Character character : chars) {
            if (children == null) return;
            stack.push(node.children);
            node = children.get(character);
            if (node == null) return;
            children = node.children;
        }
        HashMap<Character, Node<V>> popMap;
        int LastIndex = chars.length - 1;
        while (!stack.isEmpty()) {
            popMap = stack.pop();
            node = popMap.get(chars[LastIndex]);
            children = node.children;
            //node.color == RED && children != null 不能删除，是其他单词的前缀
            //children.size() > 0 不能删除，是其他单词的前缀
            if (node.color == RED) {
                //节点是红色且是被删单词的结尾
                //是其他单词的前缀，能“删除”，但不能真正删除，只能变色
                if (children != null) {
                    node.value = null;
                    node.color = BLUE;
                    return;
                }
                //直接删除
            }
            //节点是蓝色
            else if (children.size() > 0) return;
            popMap.remove(chars[LastIndex]);
            LastIndex--;
        }
    }

    @Override
    public V get(String key) {
        Node<V> node = node(key);
        return node == null ? null : node.value;
    }

    @Override
    public boolean starsWith(String prefix) {
        Node<V> node = root;
        char[] chars = getChars(prefix);
        HashMap<Character, Node<V>> children = node.children;
        for (Character character : chars) {
            if (children == null) return false;
            node = children.get(character);
            if (node == null) return false;
            children = node.children;
        }
        return true;
    }

    private void stringCheck(String str) {
        if (str == null || str.isEmpty())
            throw new IllegalArgumentException("str must not be null or str cannot be empty");
    }

    private char[] getChars(String key) {
        stringCheck(key);
        char[] chars = new char[key.length()];
        key.getChars(0, key.length(), chars, 0);
        return chars;
    }
    private Node<V> node(String key) {
        if (root == null) return null;
        Node<V> node = root;
        char[] chars = getChars(key);
        HashMap<Character, Node<V>> children = node.children;
        for (Character character : chars) {
            if (children == null) return null;
            node = children.get(character);
            if (node == null) return null;
            children = node.children;
        }
        if (node.color == RED) return node;
        return null;
    }

}
