package structure.linear.linked.queue;

import structure.linear.linked.list.BidirectionalLinkedList;
import structure.linear.operation.Deque;

public class BidirectionalLinkedQueue<E> implements Deque<E> {

    private final BidirectionalLinkedList<E> list;
    public BidirectionalLinkedQueue() {
        this.list = new BidirectionalLinkedList<>();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void enQueueRear(E element) {
        list.add(size(),element);
    }

    @Override
    public E deQueueRear() {
        return list.remove(size() - 1);
    }

    @Override
    public void enQueueFront(E element) {
        list.add(0,element);
    }

    @Override
    public E deQueueFront() {
        return list.remove(0);
    }

    @Override
    public E front() {
        return list.get(0);
    }

    @Override
    public E rear() {
        return list.get(size() - 1);
    }
}
