package structure.linearStructure.operation;

public interface List<E> {

    int ELEMENT_NOT_FOUND = -1;

    /**
     * @return 返回容器元素个数
     */
    int size();

    /**
     * @return 判断容器是否为空
     */
    boolean isEmpty();

    /**
     * @param element 传入待比较元素，
     *                在容器中查找是否包含该元素
     * @return 返回比较结果
     */
    boolean contains(E element);

    /**
     * @param element 将传入的元素添加到容器最后面
     */
    void add(E element);

    /**
     * @param index 传入一个索引，找到容器中对应索引元素
     * @return 将找到的元素返回
     */
    E get(int index);

    /**
     * @param index 传入一个索引
     * @param element 传入一个元素，用该元素替换掉对应索引的元素
     * @return 将旧的元素返回
     */
    E set(int index, E element);

    /**
     *
     * @param index 将传入元素添加到对应容器的索引位置
     * @param element 被添加的元素
     */
    void add(int index, E element);

    /**
     * @param index 删除容器中对应索引位置的元素
     * @return 将被删元素返回
     */
    E remove(int index);

    /**
     *
     * @param element 再容器中查找该元素
     * @return 返回该元素在容器中第一次出现的位置，找不到返回-1
     */
    int indexOf(E element);

    /**
     * 清空容器中所有元素，但容器还在
     */
    void clear();
}
