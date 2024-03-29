package structure.linear.heap.operation;

public interface Heap<E> {
    int size();
    boolean isEmpty();
    void clear();
    void add(E element);
    E get();
    E remove();
    E replace(E element);
    void heapify(E[] elements);
}
