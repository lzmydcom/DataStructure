package structure.linearStructure.linked.queue;

import structure.linearStructure.operation.Queue;
import structure.linearStructure.linked.impl.LinkedList;

public class LinkedQueue<E> implements Queue<E> {
    private final LinkedList<E> list;

    public LinkedQueue() {
        this.list = new LinkedList<>();
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
    public void enQueue(E element) {
        list.add(0,element);
    }

    @Override
    public E deQueue() {
        return list.remove(size() - 1);
    }

    @Override
    public E front() {
        return list.get(size() - 1);
    }

    @Override
    public void clear() {
        list.clear();
    }
}
