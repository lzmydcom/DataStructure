package structure.graph.operation;

import structure.linear.linked.queue.LinkedQueue;
import structure.linear.operation.Queue;
import structure.tree.operation.Visitor;

import java.util.*;

public class DirectedGraph<V, E> implements Graph<V, E> {

    private Map<V, Vertex<V, E>> vertices = new HashMap<>();

    private Set<Edge<V, E>> edges = new HashSet<>();

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
        while (!queue.isEmpty()){
            Vertex<V, E> deQueue = queue.deQueue();
            visitor.visit(deQueue.value);
            for (Edge<V, E> edge : deQueue.toEdges) {
                if (set.contains(edge.to)) continue;
                queue.enQueue(edge.to);
                set.add(edge.to);
            }
        }
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
