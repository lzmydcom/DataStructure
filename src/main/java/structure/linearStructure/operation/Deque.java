package structure.linearStructure.operation;

public interface Deque<E> {
    int size();
    boolean isEmpty();

    /**
     * 从队尾入队
     * @param element 入队元素
     */
    void enQueueRear(E element);

    /**
     * 从队尾出队
     */
    E deQueueRear();

    /**
     * 从队头出入队
     */
    void enQueueFront(E element);

    /**
     * 从队头出队
     * @return 出队元素
     */
    E deQueueFront();

    /**
     * 获取队头元素
     * @return 队头元素
     */
    E front();

    /**
     * 获取队尾元素
     * @return 队尾元素
     */
    E rear();



}
