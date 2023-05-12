package example.graph;

import org.junit.Test;
import structure.graph.GeneralGraph;
import structure.graph.operation.Graph;
import structure.operation.Visitor;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

public class GeneralGraphTest {
    private Graph<String, Integer> data01(){
        GeneralGraph<String, Integer> graph = new GeneralGraph<>();
        graph.addEdge("A", "B", 4);
        graph.addEdge("B", "A", 4);

        graph.addEdge("A", "H", 8);
        graph.addEdge("H", "A", 8);

        graph.addEdge("B", "C", 8);
        graph.addEdge("C", "B", 8);

        graph.addEdge("B", "H", 11);
        graph.addEdge("H", "B", 11);

        graph.addEdge("I", "H", 7);
        graph.addEdge("H", "I", 7);

        graph.addEdge("I", "G", 6);
        graph.addEdge("G", "I", 6);


        graph.addEdge("I", "C", 2);
        graph.addEdge("C", "I", 2);

        graph.addEdge("H", "G", 1);
        graph.addEdge("G", "H", 1);

        graph.addEdge("C", "D", 7);
        graph.addEdge("D", "C", 7);

        graph.addEdge("C", "F", 4);
        graph.addEdge("F", "C", 4);

        graph.addEdge("G", "F", 2);
        graph.addEdge("F", "G", 2);

        graph.addEdge("D", "F", 14);
        graph.addEdge("F", "D", 14);

        graph.addEdge("D", "E", 9);
        graph.addEdge("E", "D", 9);

        graph.addEdge("F", "E", 10);
        graph.addEdge("E", "F", 10);
        return graph;
    }

    private Graph<String, Integer> data02(){
        GeneralGraph<String, Integer> graph = new GeneralGraph<>();
        graph.addEdge("V1", "V2", 3);
        graph.addEdge("V1", "V0", 9);
        graph.addEdge("V2", "V3", 5);
        graph.addEdge("V2", "V0", 2);
        graph.addEdge("V3", "V4", 1);
        graph.addEdge("V0", "V4", 6);
        return graph;
    }

    private Graph<Integer, Integer> data03(){
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
        return graph;
    }

    private Graph<String, Integer> data04(){
        Graph<String, Integer> graph = new GeneralGraph<>();
        graph.addEdge("A", "E", 100);
        graph.addEdge("A", "B", 10);
        graph.addEdge("A", "D", 30);
        graph.addEdge("B", "C", 50);
        graph.addEdge("D", "C", 20);
        graph.addEdge("C", "E", 10);
        graph.addEdge("D", "E", 60);
        return graph;
    }

    @Test
    public void test01(){
        Graph<String, Integer> graph = data02();
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
        Graph<String, Integer> graph = data02();

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
        Graph<Integer, Integer> graph = data03();
        graph.topologicalSort(new Visitor<Integer>() {
            @Override
            public void visit(Integer integer) {
                System.out.println(integer);
            }
        });
    }

    @Test
    public void test04(){
        Graph<String, Integer> graph = data01();
        List<Graph.EdgeInfo<String, Integer>> edgeInfoSet = graph.minimumSpanningTreePrim();
        for (Graph.EdgeInfo<String, Integer> info : edgeInfoSet) {
            System.out.println(info.getFrom() + "-" + info.getTo() + " p:" + info.getWeight());
        }
    }
    @Test
    public void test05(){
        Graph<String, Integer> graph = data01();
        List<Graph.EdgeInfo<String, Integer>> edgeInfoSet = graph.minimumSpanningTreeKruskal();
        edgeInfoSet.forEach(edgeInfo -> {
            System.out.println(edgeInfo.getFrom() + "-" + edgeInfo.getTo() + " q = " +edgeInfo.getWeight());
            /*
           F-G q = 2
           A-H q = 8
           G-H q = 1
           D-E q = 9
           F-C q = 4
           I-C q = 2
           A-B q = 4
           C-D q = 7
            */
        });
    }

    @Test
    public void test06(){
        Graph<String, Integer> graph = data01();
        List<Graph.EdgeInfo<String, Integer>> edgeInfoSet = graph.minimumSpanningTreeKruskal();
        edgeInfoSet.forEach(edgeInfo -> {
            System.out.println(edgeInfo.getFrom() + "-" + edgeInfo.getTo() + " q = " +edgeInfo.getWeight());
        });
    }

    @Test
    public void test07(){
        Graph<String, Integer> graph = data04();
        Map<String, Integer> map = graph.dijkstra("A", Integer::sum);
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println("A->" + entry.getKey() + " q=" + entry.getValue());
        }
    }

    @Test
    public void test08(){
        Graph<String, Integer> graph = data04();
        Map<String, Stack<Graph.EdgeInfo<String, Integer>>> map = graph.dijkstraReturnRoute("A", Integer::sum);
        Set<Map.Entry<String, Stack<Graph.EdgeInfo<String, Integer>>>> entries = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Stack<Graph.EdgeInfo<String, Integer>>> entry : entries) {
            Stack<Graph.EdgeInfo<String, Integer>> edgeInfoStack = entry.getValue();
            stringBuilder.append("A->").append(entry.getKey()).append(" path:  ");
            do {
                Graph.EdgeInfo<String, Integer> pop = edgeInfoStack.pop();
                stringBuilder.append(pop.getTo()).append("<-").append(pop.getWeight()).append("-");
                if (edgeInfoStack.isEmpty()){
                    stringBuilder.append(pop.getFrom());
                }
            } while (!edgeInfoStack.isEmpty());
            System.out.println(stringBuilder);
            stringBuilder.setLength(0);
        }
    }

    @Test
    public void test09(){
        Graph<String, Integer> graph = data01();
        Map<String, Integer> map = graph.dijkstra("A", Integer::sum);
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            System.out.println("A->" + entry.getKey() + " q=" + entry.getValue());
        }
    }

    @Test
    public void test10(){
        Graph<String, Integer> graph = data01();
        Map<String, Stack<Graph.EdgeInfo<String, Integer>>> map = graph.dijkstraReturnRoute("A", Integer::sum);
        Set<Map.Entry<String, Stack<Graph.EdgeInfo<String, Integer>>>> entries = map.entrySet();
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Stack<Graph.EdgeInfo<String, Integer>>> entry : entries) {
            Stack<Graph.EdgeInfo<String, Integer>> edgeInfoStack = entry.getValue();
            stringBuilder.append("A->").append(entry.getKey()).append(" path:  ");
            do {
                Graph.EdgeInfo<String, Integer> pop = edgeInfoStack.pop();
                stringBuilder.append(pop.getTo()).append("<-").append(pop.getWeight()).append("-");
                if (edgeInfoStack.isEmpty()){
                    stringBuilder.append(pop.getFrom());
                }
            } while (!edgeInfoStack.isEmpty());
            System.out.println(stringBuilder);
            stringBuilder.setLength(0);
        }
    }
}