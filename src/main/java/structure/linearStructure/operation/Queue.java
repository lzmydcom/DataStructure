package structure.linearStructure.operation;

public interface Queue<E> {

    int size();

    boolean isEmpty();

    /**
     * 入队
     */
    void enQueue(E element);

    /**
     * 出队
     * @return 队列头元素
     */
    E deQueue();
    /**
     * @return 队列头元素
     */
    E front();

    void clear();
}