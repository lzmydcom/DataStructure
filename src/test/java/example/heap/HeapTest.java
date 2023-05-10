package example.heap;

import org.junit.Test;
import structure.linear.heap.binaryHeap.LargeTopHeap;
import structure.linear.heap.binaryHeap.SmallTopHeap;
import util.printer.BinaryTrees;

public class HeapTest {
    @Test
    public void heapifyTest(){
        Integer[] integers = {63, 47, 49, 53, 35, 17, 3, 59, 50,
                43, 67, 48, 76, 39, 46, 11, 41, 19, 55, 44, 28,
                88, 22, 54, 68, 74, 38, 15, 57, 87, 25, 61, 81,
                97, 94, 78, 52, 86, 2, 32, 4, 9, 73, 60, 82, 58,
                29, 66, 36, 93, 27, 84, 79, 96, 5, 40, 95, 1, 18,
                10, 34, 89, 7, 37, 30, 8};

        SmallTopHeap<Object> smallTopHeap = new SmallTopHeap<>();
        smallTopHeap.heapify(integers);
        BinaryTrees.println(smallTopHeap);

        System.out.println("============================================================================================");

        LargeTopHeap<Integer> largeTopHeap = new LargeTopHeap<>();
        largeTopHeap.heapify(integers);
        BinaryTrees.println(largeTopHeap);
    }
}
