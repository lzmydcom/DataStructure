package structure.graph;

import structure.graph.operation.Graph;
import structure.linear.array.stack.ArrayStack;
import structure.linear.array.stack.Stack;
import structure.linear.linked.queue.LinkedQueue;
import structure.linear.operation.Queue;
import structure.operation.Visitor;

import java.util.*;

public class GeneralGraph<V, E> implements Graph<V, E> {

    private final Map<V, Vertex<V, E>> vertices = new HashMap<>();

    private final Set<Edge<V, E>> edges = new HashSet<>();

    public void print(){
        vertices.forEach((V v, Vertex<V, E> vertex)->{
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
        while (iterator.hasNext()){
            Edge<V, E> edge = iterator.next();
            edge.to.fromEdges.remove(edge);
            iterator.remove();
            edges.remove(edge);
        }
        Iterator<Edge<V, E>> edgeIterator = veVertex.fromEdges.iterator();
        while (edgeIterator.hasNext()){
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
    public List<V> topologicalSort() {
        List<V> resultList = new LinkedList<>();
        Queue<Vertex<V, E>> queue = new LinkedQueue<>();
        Map<Vertex<V, E>, Integer> map = new HashMap<>();
        //将入度为0的节点加入队列中，入度不为0的节点记录到map集合中
        vertices.forEach((V v, Vertex<V,E> vertex)->{
            int size = vertex.fromEdges.size();
            if (size == 0){
                queue.enQueue(vertex);
            } else{
                map.put(vertex, size);
            }
        });
        do {
            Vertex<V, E> veVertex = queue.deQueue();
            resultList.add(veVertex.value);
            for (Edge<V, E> toEdge : veVertex.toEdges) {
                int size = map.get(toEdge.to) - 1;
                if (size == 0){
                    queue.enQueue(toEdge.to);
                }else {
                    map.put(toEdge.to, size);
                }
            }
        }while (!queue.isEmpty());
        return resultList;
    }


    private void vertexNotNullCheck(V v){
        if (v == null) throw new IllegalArgumentException("v must not be null");
    }

    private void vertexNotNullCheck(V v1, V v2){
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
