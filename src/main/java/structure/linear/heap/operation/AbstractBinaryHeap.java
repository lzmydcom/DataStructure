package structure.linear.heap.operation;

import structure.operation.Comparator;
import util.printer.BinaryTreeInfo;

import java.util.Arrays;

@SuppressWarnings("all")
public abstract class AbstractBinaryHeap<E> implements Heap<E>, BinaryTreeInfo {

    protected E[] elements;

    protected int size;

    private static final int DEFAULT_CAPACITY = 10;

    private Comparator<E> comparator;

    public AbstractBinaryHeap() {
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    public AbstractBinaryHeap(Comparator<E> comparator) {
        this.comparator = comparator;
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        Arrays.fill(elements, null);
        size = 0;
    }

    @Override
    public void add(E element){
        elementNotNullCheck(element);
        ensureCapacity(size + 1);
        elements[size] = element;
        siftUp(size);
        size++;
    }

    @Override
    public E get() {
        if (size == 0) throw new RuntimeException("容器中没有元素！");
        return elements[0];
    }

    @Override
    public E replace(E element) {
        E oldElement;
        if (size == 0){
            elements[0] = element;
            oldElement = null;
            size++;
        }else {
            oldElement = elements[0];
            elements[0] = element;
            siftDown(0);
        }
        return oldElement;
    }

    /**
     * 自下而上的下滤，批量建堆
     */
    @Override
    public void heapify(E[] elements){
        this.elements = elements;
        size = elements.length;
        for (int i = (elements.length >> 1) - 1; i >= 0; i--){
            siftDown(i);
        }
    }

    @Override
    public E remove() {
        if (size == 0) return null;
        E oldElement = elements[0];
        elements[0] = elements[size - 1];
        elements[size - 1] = null;
        size--;
        siftDown(0);
        return oldElement;
    }

    protected abstract void siftUp(int index);

    protected abstract void siftDown(int index);

    protected void elementNotNullCheck(E element) {
        if (element == null){
            throw new IllegalArgumentException("element must not be null");
        }
    }

    protected int compare(E e1, E e2){
        return comparator == null ? ((java.lang.Comparable<E>)e1).compareTo(e2) : comparator.compare(e1, e2);
    }
    protected void ensureCapacity(int newCapacity) {
        if (newCapacity <= elements.length) return;
        Object[] objects = new Object[elements.length << 1];
        System.arraycopy(elements, 0, objects, 0, elements.length);
        elements = (E[]) objects;
    }


    @Override
    public Object root() {
        return elements[0] == null ? null : 0;
    }

    @Override
    public Object left(Object node) {
        Integer index = (Integer) node;
        index = ((index << 1) + 1);
        return index >= size ? null : index;
    }

    @Override
    public Object right(Object node) {
        Integer index = (Integer) node;
        index = ((index << 1) + 2);
        return index >= size ? null : index;
    }

    @Override
    public Object string(Object node) {
        Integer index = (Integer) node;
        return elements[index];
    }
}
