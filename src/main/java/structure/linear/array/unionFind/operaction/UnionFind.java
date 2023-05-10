package structure.linear.array.unionFind.operaction;

public abstract class UnionFind {
    protected int[] parents;

    public UnionFind(int capacity) {
        if (capacity < 0) throw new IllegalArgumentException("capacity must not be empty");
        this.parents = new int[capacity];
        for (int i = 0; i < parents.length; i++) {
            parents[i] = i;
        }
    }

    /**
     *范围检查
     */
    protected void rangeCheck(int v){
        if (v < 0 || v >= parents.length){
            throw new IllegalArgumentException("v is out of bounds");
        }
    }

    /**
     *查找v所属的集合
     */
    public abstract int find(int v);

    /**
     *合并v1、v2所属的集合, 左边向右边合并
     */
    public abstract void union(int v1, int v2);

    /**
     *判断v1， v2是否属于同一个集合
     */
    public boolean isSame(int v1, int v2){
        return find(v1) == find(v2);
    }
}
