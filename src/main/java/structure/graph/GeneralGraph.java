package structure.graph;

import structure.graph.operation.Graph;
import structure.linear.array.stack.ArrayStack;
import structure.linear.array.stack.Stack;
import structure.linear.array.unionFind.genericUnionFind.GenericQuickUnion;
import structure.linear.heap.binaryHeap.SmallTopHeap;
import structure.linear.heap.queue.PriorityQueue;
import structure.linear.linked.queue.LinkedQueue;
import structure.linear.operation.Queue;
import structure.operation.Comparator;
import structure.operation.Visitor;

import java.util.*;

@SuppressWarnings("all")
public class GeneralGraph<V, E> implements Graph<V, E> {

    private Comparator<E> comparator;

    public GeneralGraph(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    public GeneralGraph() {
    }

    private final Map<V, Vertex<V, E>> vertices = new HashMap<>();

    private final Set<Edge<V, E>> edges = new HashSet<>();

    public void print() {
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            System.out.println(v);
            System.out.println("fromEdges:" + vertex.fromEdges);
            System.out.println("toEdges:" + vertex.toEdges);
        });
        edges.forEach(System.out::println);
    }

    @Override
    public int edges() {
        return edges.size();
    }

    @Override
    public int vertices() {
        return vertices.size();
    }

    @Override
    public void addVertex(V v) {
        vertexNotNullCheck(v);
        if (vertices.containsKey(v)) return;
        vertices.put(v, new Vertex<>(v));
    }

    @Override
    public void addEdge(V from, V to, E weight) {
        vertexNotNullCheck(from, to);

        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) {
            fromVertex = new Vertex<>(from);
            vertices.put(from, fromVertex);
        }
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) {
            toVertex = new Vertex<>(to);
            vertices.put(to, toVertex);
        }
        Edge<V, E> veEdge = new Edge<>(fromVertex, toVertex);
        veEdge.weight = weight;
        edges.add(veEdge);
        fromVertex.toEdges.add(veEdge);
        toVertex.fromEdges.add(veEdge);
    }

    @Override
    public void addEdge(V from, V to) {
        addEdge(from, to, null);
    }

    @Override
    public void removeVertex(V v) {
        vertexNotNullCheck(v);
        Vertex<V, E> veVertex = vertices.get(v);
        if (veVertex == null) return;
        Iterator<Edge<V, E>> iterator = veVertex.toEdges.iterator();
        while (iterator.hasNext()) {
            Edge<V, E> edge = iterator.next();
            edge.to.fromEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }
        Iterator<Edge<V, E>> edgeIterator = veVertex.fromEdges.iterator();
        while (edgeIterator.hasNext()) {
            Edge<V, E> edge = edgeIterator.next();
            edge.from.toEdges.remove(edge);
            edgeIterator.remove();
            edges.remove(edge);
        }
    }

    @Override
    public void removeEdge(V from, V to) {
        Vertex<V, E> fromVertex = vertices.get(from);
        if (fromVertex == null) return;
        Vertex<V, E> toVertex = vertices.get(to);
        if (toVertex == null) return;

        Edge<V, E> veEdge = new Edge<>(fromVertex, toVertex);
        fromVertex.toEdges.remove(veEdge);
        toVertex.fromEdges.remove(veEdge);
        edges.remove(veEdge);
    }

    @Override
    public void breadthFirstSearch(V begin, Visitor<V> visitor) {
        Vertex<V, E> veVertex = vertices.get(begin);
        if (veVertex == null) return;
        Queue<Vertex<V, E>> queue = new LinkedQueue<>();
        Set<Vertex<V, E>> set = new HashSet<>();
        queue.enQueue(veVertex);
        set.add(veVertex);
        do {
            Vertex<V, E> deQueue = queue.deQueue();
            if (Visitor.stop) return;
            visitor.visit(deQueue.value);
            for (Edge<V, E> edge : deQueue.toEdges) {
                if (set.contains(edge.to)) continue;
                queue.enQueue(edge.to);
                set.add(edge.to);
            }
        } while (!queue.isEmpty());
    }

    @Override
    public void depthFirstSearch(V begin, Visitor<V> visitor) {
        Vertex<V, E> veVertex = vertices.get(begin);
        //如果传入的节点在图中没有，就没必要进行深度优先搜索
        if (veVertex == null) return;
        Stack<Vertex<V, E>> stack = new ArrayStack<>();

        visitor.visit(veVertex.value);
        stack.push(veVertex);

        //Set集合中的元素不重复，能用来检查节点是否访问过
        Set<Vertex<V, E>> set = new HashSet<>();
        set.add(veVertex);

        Vertex<V, E> to;
        boolean isANewNode = false;

        do {
            Vertex<V, E> top = stack.top();

            for (Edge<V, E> edge : top.toEdges) {
                to = edge.to;
                //如果是访问过的节点就不能入栈，直接跳过
                if (set.contains(to)) continue;
                //访问过的节点加入到Set集合中，确保下次遇到已访问节点时直接跳过
                set.add(to);
                //没访问过的节点，对该节点进行访问并加入到栈中
                if (Visitor.stop) return;
                visitor.visit(to.value);
                stack.push(to);
                //此次访问到的节点是新节点，标记为true
                isANewNode = true;
                break;
            }
            //下一次访问从此新节点开始，不能将新节点pop，将节点上所有路径都访问完了的节点pop
            if (!isANewNode) {
                stack.pop();
            }
            //刷新标记
            isANewNode = false;
        } while (!stack.isEmpty());

    }

    @Override
    public void topologicalSort(Visitor<V> visitor) {
        Queue<Vertex<V, E>> queue = new LinkedQueue<>();
        Map<Vertex<V, E>, Integer> map = new HashMap<>();
        //将入度为0的节点加入队列中，入度不为0的节点记录到map集合中
        vertices.forEach((V v, Vertex<V, E> vertex) -> {
            int size = vertex.fromEdges.size();
            if (size == 0) {
                queue.enQueue(vertex);
            } else {
                map.put(vertex, size);
            }
        });
        do {
            Vertex<V, E> veVertex = queue.deQueue();
            if (Visitor.stop) return;
            visitor.visit(veVertex.value);
            for (Edge<V, E> toEdge : veVertex.toEdges) {
                int size = map.get(toEdge.to) - 1;
                if (size == 0) {
                    queue.enQueue(toEdge.to);
                } else {
                    map.put(toEdge.to, size);
                }
            }
        } while (!queue.isEmpty());
    }

    @Override
    public List<EdgeInfo<V, E>> minimumSpanningTreePrim() {
        Iterator<Vertex<V, E>> iterator = vertices.values().iterator();
        if (!iterator.hasNext()) return null;
        Vertex<V, E> veVertex = iterator.next();
        List<EdgeInfo<V, E>> edgeInfoList = new LinkedList<>();
        Set<Vertex<V, E>> vertexSet = new HashSet<>();
        SmallTopHeap<Edge<V, E>> smallTopHeap = new SmallTopHeap<>(new Comparator<Edge<V, E>>() {
            @Override
            public int compare(Edge<V, E> e1, Edge<V, E> e2) {
                return comparator == null ?
                        ((Comparable<E>) e1.weight).compareTo(e2.weight)
                        : comparator.compare(e1.weight, e2.weight);
            }
        });
        int verticeSize = vertices();
        do {
            vertexSet.add(veVertex);
            for (Edge<V, E> edge : veVertex.toEdges) {
                if (vertexSet.contains(edge.from) && vertexSet.contains(edge.to)) continue;
                smallTopHeap.add(edge);
            }
            Edge<V, E> veEdge;
            do {
                veEdge = smallTopHeap.remove();
                if (vertexSet.contains(veEdge.from) && vertexSet.contains(veEdge.to)) continue;
                break;
            } while (!smallTopHeap.isEmpty());
            EdgeInfo<V, E> veEdgeInfo = veEdge.edgeInfo();
            edgeInfoList.add(veEdgeInfo);
            veVertex = veEdge.to;
        } while (edgeInfoList.size() != verticeSize - 1);

        return edgeInfoList;
    }

    @Override
    public List<EdgeInfo<V, E>> minimumSpanningTreeKruskal() {
        GenericQuickUnion<Vertex<V, E>> gqu = new GenericQuickUnion<>();
        vertices.values().forEach(vertex -> gqu.initV(vertex));
        List<EdgeInfo<V, E>> edgeInfoList = new LinkedList<>();
        SmallTopHeap<Edge<V, E>> smallTopHeap = new SmallTopHeap<>(new Comparator<Edge<V, E>>() {
            @Override
            public int compare(Edge<V, E> e1, Edge<V, E> e2) {
                return comparator == null ?
                        ((Comparable<E>) e1.weight).compareTo(e2.weight)
                        : comparator.compare(e1.weight, e2.weight);
            }
        });
        int vertexSize = vertices() - 1;
        edges.forEach(edge -> smallTopHeap.add(edge));

        do {
            Edge<V, E> remove = smallTopHeap.remove();
            if (gqu.isSame(remove.from, remove.to)) continue;
            edgeInfoList.add(remove.edgeInfo());
            gqu.union(remove.from, remove.to);
        } while (!smallTopHeap.isEmpty() && edgeInfoList.size() != vertexSize);

        return edgeInfoList;
    }

    @Override
    public Map<V, E> dijkstra(V begin, Operation<E> operation) {
        Vertex<V, E> veVertex = vertices.get(begin);
        if (veVertex == null) return null;

        Map<V, E> map = new HashMap<>();
        PriorityQueue<Edge<V, E>> queue = new PriorityQueue<>(new Comparator<Edge<V, E>>() {
            @Override
            public int compare(Edge<V, E> e1, Edge<V, E> e2) {
                return comparator == null ?
                        ((Comparable<E>) e1.weight).compareTo(e2.weight)
                        : comparator.compare(e1.weight, e2.weight);
            }
        });

        int vertexSize = vertices();
        veVertex.toEdges.forEach(veEdge -> queue.enQueue(veEdge));

        do {
            Edge<V, E> veEdge = queue.deQueue();
            E temp;

            if ((temp = map.get(veEdge.to.value)) != null || veEdge.to.value.equals(begin)) {
                //来到这说明有更好的到达路径已经在map中存在，该路径不进行任何处理
                continue;
            }
            //将边的顶点和权值放到map中
            map.put(veEdge.to.value, veEdge.weight);
            E weight = veEdge.weight;
            veEdge.to.toEdges.forEach(edge -> {
                //将权值进行处理后赋值给新创建出来的边里(例如 加减，拼接等...)
                //为了不破坏原本的图的权值，创建一个新的边加入到优先级队列中
                Edge<V, E> newEdge = new Edge<>(edge.from, edge.to, operation.operate(weight, edge.weight));
                queue.enQueue(newEdge);
            });
            //所有可能的边都已经遍历完或者所有的顶点都已经找到最佳路径匹配，退出循环
        } while (!queue.isEmpty() && map.size() != vertexSize - 1);
        return map;
    }

    @Override
    public Map<V, java.util.Stack<EdgeInfo<V, E>>> dijkstraReturnRoute(V begin, Operation<E> operation) {
        Vertex<V, E> veVertex = vertices.get(begin);
        if (veVertex == null) return null;

        Map<V, java.util.Stack<EdgeInfo<V, E>>> map = new HashMap<>();
        PriorityQueue<Edge<V, E>> queue = new PriorityQueue<>(new Comparator<Edge<V, E>>() {
            @Override
            public int compare(Edge<V, E> e1, Edge<V, E> e2) {
                return comparator == null ?
                        ((Comparable<E>) e1.weight).compareTo(e2.weight)
                        : comparator.compare(e1.weight, e2.weight);
            }
        });

        int vertexSize = vertices();
        veVertex.toEdges.forEach(veEdge -> queue.enQueue(veEdge));

        do {
            Edge<V, E> veEdge = queue.deQueue();
            java.util.Stack<EdgeInfo<V, E>> stack;
            if ((stack = map.get(veEdge.to.value)) != null || veEdge.to.value.equals(begin)) {
                //来到这说明有更好的到达路径已经在map中存在，该路径不进行任何处理
                continue;
            } else {
                //能来到这说明map中还是第一次添加该节点的路径
                java.util.Stack<EdgeInfo<V, E>> fromVertexStack = map.get(veEdge.from.value);
                stack = new java.util.Stack<>();
                if (fromVertexStack != null){
                    for (EdgeInfo<V, E> veEdgeInfo : fromVertexStack) {
                        stack.push(veEdgeInfo);
                    }
                }
            }
            //将边的顶点和权值放到map中
            stack.push(veEdge.edgeInfo());
            map.put(veEdge.to.value, stack);
            E weight = veEdge.weight;
            veEdge.to.toEdges.forEach(edge -> {
                //将权值进行处理后赋值给新创建出来的边里(例如 加减，拼接等...)
                //为了不破坏原本的图的权值，创建一个新的边加入到优先级队列中
                Edge<V, E> newEdge = new Edge<>(edge.from, edge.to, operation.operate(weight, edge.weight));
                queue.enQueue(newEdge);
            });
            //所有可能的边都已经遍历完或者所有的顶点都已经找到最佳路径匹配，退出循环
        } while (!queue.isEmpty() && map.size() != vertexSize - 1);
        return map;
    }

    private int compare(E e1, E e2) {
        return comparator == null ? ((Comparable<E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    private void vertexNotNullCheck(V v) {
        if (v == null) throw new IllegalArgumentException("v must not be null");
    }

    private void vertexNotNullCheck(V v1, V v2) {
        if (v1 == null || v2 == null) throw new IllegalArgumentException("v must not be null");
    }

    private static class Vertex<V, E> {
        public Vertex(V value) {
            this.value = value;
        }

        V value;
        Set<Edge<V, E>> fromEdges = new HashSet<>();
        Set<Edge<V, E>> toEdges = new HashSet<>();

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vertex<?, ?> vertex = (Vertex<?, ?>) o;
            return value.equals(vertex.value);
        }

        @Override
        public int hashCode() {
            return value.hashCode();
        }

        @Override
        public String toString() {
            return "Vertex{" +
                    "value=" + value +
                    '}';
        }
    }

    private static class Edge<V, E> {
        Vertex<V, E> from;

        Vertex<V, E> to;

        E weight;

        public Edge(Vertex<V, E> from, Vertex<V, E> to) {
            this.from = from;
            this.to = to;
        }

        public Edge(Vertex<V, E> from, Vertex<V, E> to, E weight) {
            this.from = from;
            this.to = to;
            this.weight = weight;
        }

        public Graph.EdgeInfo<V, E> edgeInfo() {
            return new EdgeInfo<>(from.value, to.value, weight);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Edge<?, ?> edge = (Edge<?, ?>) o;
            return Objects.equals(from, edge.from) && Objects.equals(to, edge.to);
        }

        @Override
        public int hashCode() {
            return from.hashCode() * 31 + to.hashCode();
        }

        @Override
        public String toString() {
            return "Edge{" +
                    "from=" + from +
                    ", to=" + to +
                    ", weight=" + weight +
                    '}';
        }
    }
}
