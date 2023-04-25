package example.collection.map;

import org.junit.Assert;
import org.junit.Test;
import structure.treeStructure.collection.map.TreeMap;
import structure.treeStructure.collection.operation.Visitor;
import util.printer.BinaryTrees;

public class TreeMapTest {
    @Test
    public void TreeMapTraversalTest(){
        TreeMap<Integer, Object> map = new TreeMap<>();
        map.put(34,"hello world");
        map.put(45,"zs");
        map.put(89,"ls");
        map.put(82,"ww");
        map.put(67,"zl");
        map.put(98,"jack");
        map.put(98, "rose");
        BinaryTrees.println(map);
        System.out.println(map.size());
        map.traversal(new Visitor<Integer, Object>() {
            @Override
            public boolean visit(Integer key, Object value) {
                System.out.println("key:" +key +"value:" +value);
                return key == 67;
            }
        });

        Assert.assertFalse(map.containsKey(8883));
        Assert.assertTrue(map.containsKey(98));
        Assert.assertTrue(map.containsValue("rose"));
        Assert.assertFalse(map.containsValue(100));
    }

    @Test
    public void TreeMapTest01(){
        TreeMap<Integer, String> treeMap = new TreeMap<>();
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        for (Integer integer:integers){
            treeMap.put(integer, "<hello>");
            if (integer == 62){
                treeMap.put(25,"鸡你太美！");
            }
        }
        BinaryTrees.println(treeMap);


        Assert.assertTrue(treeMap.containsKey(19));
        Assert.assertFalse(treeMap.containsKey(101));
        Assert.assertFalse(treeMap.containsValue("鸡你太美？"));
        Assert.assertTrue(treeMap.containsValue("鸡你太美！"));

        for (Integer integer:integers){
            treeMap.remove(integer);
        }
        BinaryTrees.println(treeMap);
        System.out.println(treeMap.size());


    }
}
