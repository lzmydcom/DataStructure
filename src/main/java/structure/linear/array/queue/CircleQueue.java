package structure.linear.array.queue;

import structure.linear.operation.Queue;
@SuppressWarnings("all")
public class CircleQueue<E> implements Queue<E> {
    private int front = 0;
    private int rear = 0;

    private int size = 0;

    private static final int DEFAULT_CAPACITY = 10;

    private E[] elements;


    public CircleQueue() {
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
    public void enQueue(E element) {
        ensureCapacity(size + 1);
        int index = realIndex(size + front);
        elements[index] = element;
        if (!isEmpty()){
            rear = realIndex(rear + 1);
        }
        size++;
    }

    private void ensureCapacity(int cacpcity){
        if (cacpcity > elements.length){
            //开始扩容
            int newCacpcity = elements.length + (elements.length >> 1);
            Object[] newElements =  new Object[newCacpcity];
            if (front == 0){
                System.arraycopy((Object) elements, 0, newElements,0,elements.length);
                elements = (E[]) newElements;
                return;
            }
            /*for (int i = front; i < elements.length; i++){
                newElements[i] = elements[i];
            }*/
            System.arraycopy((Object) elements, front, newElements,front,elements.length - front);
            for (int i = 0; i <= rear; i++){
                newElements[realIndex(elements.length + i, newCacpcity)] = elements[i];
            }
            elements = (E[]) newElements;
            rear = realIndex(size + front - 1);
        }
    }
    private int realIndex(int index,int cacpcity){
        return index - (index >= cacpcity ? cacpcity : 0);
    }
    private int realIndex(int index){
        return index - (index >= elements.length ? elements.length : 0);
    }
    @Override
    public E deQueue() {
        E remove = elements[front];
        elements[front] = null;
        if (size != 1){
            front = realIndex(front + 1);
        }
        size--;
        return remove;
    }

    @Override
    public E front() {
        return elements[front];
    }

    @Override
    public void clear() {
        front = 0;
        rear = 0;
        elements = (E[]) new Object[DEFAULT_CAPACITY];
    }
}
