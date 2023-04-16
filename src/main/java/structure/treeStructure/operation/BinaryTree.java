package structure.treeStructure.operation;

public interface BinaryTree<E> {
    /**
     * @return 元素的数量
     */
    int size();

    /**
     * @return 判断容器是否为空
     */
    boolean isEmpty();

    /**
     * 清空所有元素
     */
    void clear();

    /**
     * @param element 添加元素
     */
    void add(E element);

    /**
     * @param element 删除元素
     */
    void remove(E element);

    /**
     * @param element 是否包含某元素
     * @return 返回结果
     */
    boolean contains(E element);
}
