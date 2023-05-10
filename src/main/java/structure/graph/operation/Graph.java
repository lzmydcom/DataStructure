package structure.graph.operation;

import structure.operation.Visitor;

public interface Graph<V, E> {
    /**
     *返回边数
     */
    int edges();

    /**
     *返回顶点数目
     */
    int vertices();

    /**
     *添加一个顶点
     */
    void addVertex(V v);

    void addEdge(V from, V to, E weight);

    /**
     *添加一条边
     */
    void addEdge(V from, V to);

    /**
     * 删除该节点的所有边
     */
    void removeVertex(V v);

    /**
     *删除一条边
     */
    void removeEdge(V from, V to);

    /**
     * 广度优先搜索
     */
    void breadthFirstSearch(V begin, Visitor<V> visitor);

    /**
     * 深度优先搜索
     */
    void depthFirstSearch(V begin, Visitor<V> visitor);
}
