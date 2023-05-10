package example.graph;

import org.junit.Test;
import structure.graph.operation.GeneralDiagram;
import structure.operation.Visitor;

public class GeneralDiagramTest {
    @Test
    public void test01(){
        GeneralDiagram<String, Integer> graph = new GeneralDiagram<>();
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
        GeneralDiagram<String, Integer> graph = new GeneralDiagram<>();
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
}