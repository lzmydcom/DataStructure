package structure.treeStructure.heap.queue;

import structure.treeStructure.heap.binaryHeap.SmallTopHeap;
import structure.treeStructure.heap.operation.Queue;
import structure.treeStructure.operation.Comparator;


/**
 * 基于小顶堆实现的优先级队列
 * @param <E>
 */
public class PriorityQueue<E> implements Queue<E> {

    private final SmallTopHeap<E> heap;

    public PriorityQueue() {
        this(null);
    }
    public PriorityQueue(Comparator<E> comparator) {
        heap = new SmallTopHeap<>(comparator);
    }

    @Override
    public int size() {
        return heap.size();
    }

    @Override
    public boolean isEmpty() {
        return heap.isEmpty();
    }

    @Override
    public void enQueue(E element) {
        heap.add(element);
    }

    @Override
    public E deQueue() {
        return heap.remove();
    }

    @Override
    public E front() {
        return heap.get();
    }

    @Override
    public void clear() {
        heap.clear();
    }
}
