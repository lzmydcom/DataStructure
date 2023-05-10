package example.graph;

import org.junit.Test;
import structure.graph.GeneralGraph;
import structure.operation.Visitor;

import java.util.List;

public class GeneralGraphTest {
    @Test
    public void test01(){
        GeneralGraph<String, Integer> graph = new GeneralGraph<>();
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

    @Test
    public void test02(){
        GeneralGraph<String, Integer> graph = new GeneralGraph<>();
        graph.addEdge("V1", "V2", 3);
        graph.addEdge("V1", "V0", 9);
        graph.addEdge("V2", "V3", 5);
        graph.addEdge("V2", "V0", 2);
        graph.addEdge("V3", "V4", 1);
        graph.addEdge("V0", "V4", 6);

        graph.depthFirstSearch("V1", new Visitor<String>() {
            @Override
            public void visit(String s) {
                if ("V4".equals(s)) Visitor.stop = true;
                System.out.println(s);
            }
        });
    }
    @Test
    public void test03(){
        GeneralGraph<Integer, Integer> graph = new GeneralGraph<>();
        graph.addEdge(0, 2);
        graph.addEdge(1, 0);
        graph.addEdge(3, 1);
        graph.addEdge(3, 5);
        graph.addEdge(3, 7);
        graph.addEdge(5, 7);
        graph.addEdge(2, 5);
        graph.addEdge(2, 6);
        graph.addEdge(7, 6);
        graph.addEdge(6, 4);

        List<Integer> list = graph.topologicalSort();
        for (Integer integer : list) {
            System.out.println(integer);
        }
    }
}