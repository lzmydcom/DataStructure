package example.collection.set;

import org.junit.Assert;
import org.junit.Test;
import structure.treeStructure.collection.set.TreeSet;
import structure.treeStructure.operation.Visitor;
import util.printer.BinaryTrees;

public class TreeSetTest {
    @Test
    public void addAndRemoveTest(){
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        TreeSet<Integer> treeSet = new TreeSet<>();
        for (Integer integer:integers){
            treeSet.add(integer);
        }
        BinaryTrees.println(treeSet);

        for (Integer integer:integers){
            treeSet.remove(integer);
        }

        BinaryTrees.println(treeSet);
    }

    @Test
    public void TraversalTest(){
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        TreeSet<Integer> treeSet = new TreeSet<>();
        for (Integer integer:integers){
            treeSet.add(integer);
        }

        treeSet.traversal(new Visitor<Integer>() {
            @Override
            public void visit(Integer integer) {
                System.out.println(integer);
                if (integer.equals(44)) stop = true;
            }
        });

    }

    @Test
    public void containsTest(){
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        TreeSet<Integer> treeSet = new TreeSet<>();
        for (Integer integer:integers){
            treeSet.add(integer);
        }

        Assert.assertTrue(treeSet.contains(78));
        Assert.assertTrue(treeSet.contains(11));
        Assert.assertTrue(treeSet.contains(16));
        Assert.assertFalse(treeSet.contains(100));
        Assert.assertFalse(treeSet.contains(200));
        Assert.assertFalse(treeSet.contains(0));

    }

}
