package list.impl;

import list.List;
import lombok.extern.log4j.Log4j;
@Log4j
public class ArrayList implements List<Object> {

    private static boolean OPEN_LOG = false;
    private int size = 0;
    private Object[] elements;

    private static final int DEFAULT_CAPACITY = 10;
    private static final int ELEMENT_NOT_FOUND = -1;

    public ArrayList() {
        this(DEFAULT_CAPACITY);
    }

    public ArrayList(int capacity) {
        capacity = Math.max(capacity, DEFAULT_CAPACITY);
        elements = new Object[capacity];
    }

    public static void openLog() {
        OPEN_LOG = true;
    }

    @Override
    public int size() {
        return size;
    }

    private void dynamicExpansion(int capacity) {
        int oldCapacity = elements.length;
        if (oldCapacity >= capacity) return;
        //开始扩容
        if (OPEN_LOG) System.out.println("开始扩容 oldCapacity:" + oldCapacity + "  newCapacity:" + (oldCapacity + (oldCapacity >> 1)));

        Object[] objects = new Object[oldCapacity + (oldCapacity >> 1)];
        System.arraycopy(elements, 0, objects, 0, elements.length);
        elements = objects;
    }

    private void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + "," + "size:" + size);
        }
    }

    private void indexCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index:" + index + "," + "size:" + size);
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    @Override
    public void add(Object element) {
        dynamicExpansion(size + 1);
        elements[size] = element;
        size++;
    }

    @Override
    public Object get(int index) {
        indexCheck(index);
        return elements[index];
    }

    @Override
    public Object set(int index, Object element) {
        indexCheck(index);
        Object old = elements[index];
        elements[index] = element;
        return old;
    }

    @Override
    public void add(int index, Object element) {
        indexCheckForAdd(index);
        dynamicExpansion(size + 1);
        if (size - index > 0) System.arraycopy(elements, index, elements, index + 1, size - index);
        elements[index] = element;
        size++;
    }

    @Override
    public Object remove(int index) {
        indexCheck(index);
        Object oldObj = elements[index];
        if (size - 1 - index > 0) System.arraycopy(elements, index + 1, elements, index, size - 1 - index);
        elements[--size] = null;
        return oldObj;
    }

    @Override
    public int indexOf(Object element) {
        if (element == null){
            for (int i = 0; i < size; i++) {
                if (elements[i] == null) {
                    return i;
                }
            }
        }else {
            for (int i = 0; i < size; i++) {
                if (elements[i].equals(element)) {
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
                stringBuilder.append(elements[i]).append("]");
            } else stringBuilder.append(elements[i]).append(",");
        }
        return stringBuilder.toString();
    }
}
