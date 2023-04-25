package example.heap;

import org.junit.Assert;
import org.junit.Test;
import structure.treeStructure.heap.binaryHeap.LargeTopHeap;
import structure.treeStructure.heap.operation.AbstractBinaryHeap;
import util.printer.BinaryTrees;

public class LargeTopHeapTest {
    @Test
    public void LargeTopHeapTest01() {
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15, 99};

        AbstractBinaryHeap<Integer> heap = new LargeTopHeap<>();
        for (Integer integer : integers) {
            if (integer == 28) {
                BinaryTrees.println(heap);
                heap.add(integer);
            } else {
                heap.add(integer);
            }
        }

        System.out.println("========================================================================");
        BinaryTrees.println(heap);
        System.out.println(heap.get());

        System.out.println("========================================================================");
        int size = heap.size();
        for (int i = 0; i < size; i++) {
            BinaryTrees.println(heap);
            heap.remove();
        }
        BinaryTrees.println(heap);
        System.out.println(heap.size());
    }

    @Test
    public void LargeTopHeapTest02() {
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15, 99};


        AbstractBinaryHeap<Integer> heap = new LargeTopHeap<>();

        heap.replace(20);
        Assert.assertEquals(20, (int) heap.get());

        for (Integer integer : integers) {
            heap.add(integer);
        }
        Assert.assertEquals(integers.length + 1, heap.size());

        BinaryTrees.println(heap);
        System.out.println("========================================================================");
        System.out.println(heap.replace(100));
        Assert.assertEquals(100, (int) heap.get());
        System.out.println(heap.replace(10));
        BinaryTrees.println(heap);
        Assert.assertEquals(integers.length + 1, heap.size());
    }

}
