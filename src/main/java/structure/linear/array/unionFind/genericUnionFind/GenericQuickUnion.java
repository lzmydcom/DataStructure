package structure.linear.array.unionFind.genericUnionFind;

import structure.tree.collection.map.HashMap;
import structure.tree.collection.operation.Map;

import java.util.Objects;

public class GenericQuickUnion<V> {
    private final Map<V, Node<V>> nodes = new HashMap<>();

    public void initQuickUnion(V[] vs) {
        for (V v : vs) {
            nodes.put(v, new Node<>(v));
        }
    }

    public void initV(V v) {
        nodes.put(v, new Node<>(v));
    }

    public V find(V v) {
        nodeNotNullCheck(v);
        return findNode(v).value;
    }

    private Node<V> findNode(V v) {
        Node<V> vNode = nodes.get(v);

        while (!Objects.equals(vNode, vNode.parent)) {
            vNode.parent = vNode.parent.parent;
            vNode = vNode.parent;
        }
        return vNode;
    }


    public void union(V v1, V v2) {
        Node<V> p1 = findNode(v1);
        Node<V> p2 = findNode(v2);
        if (p1.value.equals(p2.value)) return;
        if (p1.rank < p2.rank) {
            p1.parent = p2;
        } else if (p1.rank > p2.rank) {
            p2.parent = p1;
        } else {
            p1.parent = p2;
            p2.rank++;
        }
    }

    public boolean isSame(V v1, V v2) {
        return find(v1).equals(find(v2));
    }

    private void nodeNotNullCheck(V v) {
        if (v == null) throw new IllegalArgumentException("v must not be null");
    }

    private static class Node<V> {
        V value;
        int rank = 1;
        Node<V> parent = this;

        public Node(V value) {
            this.value = value;
        }
    }
}
