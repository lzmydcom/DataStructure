package structure.linear.array.unionFind.quickUnion;

import structure.linear.array.unionFind.operaction.UnionFind;

public class QuickUnion extends UnionFind {
    public QuickUnion(int capacity) {
        super(capacity);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        do {
            v = parents[v];
        }while (v != parents[v]);
        return v;
    }

    @Override
    public void union(int v1, int v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return;
        parents[p1] = v2;
    }
}
