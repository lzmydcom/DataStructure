package structure.linear.array.stack;

import structure.linear.array.list.ArrayList;

public class ArrayStack<E> implements Stack<E>{

    private final ArrayList<E> list;

    public ArrayStack() {
        list = new ArrayList<>();
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
    public void push(E element) {
        list.add(element);
    }

    @Override
    public E pop() {
        return list.remove(size() - 1);
    }

    @Override
    public E top() {
        return list.get(size() - 1);
    }
}
