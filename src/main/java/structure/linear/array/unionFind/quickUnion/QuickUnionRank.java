package structure.linear.array.unionFind.quickUnion;

import structure.linear.array.unionFind.operaction.UnionFind;

@SuppressWarnings("all")
public class QuickUnionRank extends UnionFind {
    private final int[] ranks;
    public QuickUnionRank(int capacity){
        super(capacity);
        ranks = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        do {
            v = parents[v];
        }while (parents[v] != v);
        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        if (ranks[p1] < ranks[p2]){
            parents[p1] = p2;
        } else if (ranks[p1] > ranks[p2]) {
            parents[p2] = p1;
        } else {
            parents[p1] = p2;
            ranks[p2]++;
        }
    }
}
