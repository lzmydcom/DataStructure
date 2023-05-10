package graph;

import org.junit.Test;
import structure.graph.operation.DirectedGraph;
import structure.tree.operation.Visitor;

public class DirectedGraphTest {
    @Test
    public void test01(){
        DirectedGraph<String, Integer> graph = new DirectedGraph<>();
        graph.addEdge("V1", "V2", 3);
        graph.addEdge("V1", "V0", 9);
        graph.addEdge("V2", "V3", 5);
        graph.addEdge("V2", "V0", 2);
        graph.addEdge("V3", "V4", 1);
        graph.addEdge("V0", "V4", 6);
        graph.print();
        System.out.println("================================================");
        graph.breadthFirstSearch("V1", new Visitor<String>() {
            @Override
            public void visit(String s) {
                System.out.println(s);
            }
        });
        System.out.println("================================================");
        graph.removeVertex("V0");
        graph.print();
    }
}
