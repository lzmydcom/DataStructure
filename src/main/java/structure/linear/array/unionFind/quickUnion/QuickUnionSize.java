package structure.linear.array.unionFind.quickUnion;

import structure.linear.array.unionFind.operaction.UnionFind;

import java.util.Arrays;

public class QuickUnionSize extends UnionFind {
    private final int[] sizes;

    public QuickUnionSize(int capacity){
        super(capacity);
        sizes = new int[capacity];
        Arrays.fill(sizes, 1);
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
        if (sizes[p1] > sizes[p2]){
            parents[p2] = p1;
            sizes[p1] = sizes[p1] + sizes[p2];
        }else {
            parents[p1] = p2;
            sizes[p2] = sizes[p1] + sizes[p2];
        }
    }
}