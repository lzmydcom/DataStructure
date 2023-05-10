package structure.linear.array.unionFind.quickUnion;

import structure.linear.array.unionFind.operaction.UnionFind;

import java.util.Arrays;

@SuppressWarnings("all")
public class QuickUnionRankPathCompression extends UnionFind {

    private final int[] ranks;

    public QuickUnionRankPathCompression(int capacity) {
        super(capacity);
        ranks = new int[capacity];
        Arrays.fill(ranks, 1);
    }

    @Override
    public int find(int v) {
        rangeCheck(v);
        int element = v;
        do {
           v = parents[v];
        }while (v != parents[v]);

        while (parents[element] != v) {
            element = parents[element];
            parents[element] = v;
        }
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
