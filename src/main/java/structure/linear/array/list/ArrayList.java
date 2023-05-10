package structure.linear.array.list;

import structure.linear.operation.AbstractList;

@SuppressWarnings({"unchecked"})
public class ArrayList<E> extends AbstractList<E> {
    protected E[] elements;

    private static final int DEFAULT_CAPACITY = 10;
    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        if (capacity > 0){
            elements = (E[]) new Object[capacity];
        } else if (capacity == 0) {
            elements = (E[]) new Object[]{};
        }else {
            throw new IllegalArgumentException("Illegal Capacity: " + capacity);
        }
    }

    protected void dynamicExpansion(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        //开始扩容
        if (OPEN_LOG) System.out.println("开始扩容 oldCapacity:" + oldCapacity + "  newCapacity:" + (oldCapacity + (oldCapacity >> 1)));

        E[] Es = (E[]) new Object[oldCapacity + (oldCapacity >> 1)];
        System.arraycopy(elements, 0, Es, 0, elements.length);
        elements = Es;
    }


    @Override
    public E get(int index) {
        indexCheck(index);
        return elements[index];
    }

    @Override
    public E set(int index, E element) {
        indexCheck(index);
        E old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, E element) {
        indexCheckForAdd(index);
        dynamicExpansion(size + 1);
        if (size - index > 0) System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public E remove(int index) {
        indexCheck(index);
        E oldObj = elements[index];
        if (size - 1 - index > 0) System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        elements[--size] = null;
        return oldObj;
    }

    @Override
    public int indexOf(E element) {
        if (element == null){
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (element.equals(elements[i])) {
                    return i;
                }
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        //清空数组，让垃圾回收器回收对象
        for (int i = 0; i < size; i++){
            elements[i] = null;
        }
        size = 0;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("size = " + size + " array = [");
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                stringBuilder.append(elements[i].toString()).append("]");
            } else stringBuilder.append(elements[i].toString()).append(",");
        }
        return stringBuilder.toString();
    }

    public int getCapacity(){
        return elements.length;
    }
}
