package structure.linear.operation;


public abstract class AbstractList<E> implements List<E> {
    protected int size;
    protected static boolean OPEN_LOG = false;

    public static void openLog() {
        OPEN_LOG = true;
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
    public boolean contains(E element) {
        return indexOf(element) != ELEMENT_NOT_FOUND;
    }

    @Override
    public void add(E element) {
        add(size,element);
    }

    protected void indexCheck(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index:" + index + "," + "size:" + size);
        }
    }

    protected void indexCheckForAdd(int index) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index:" + index + "," + "size:" + size);
        }
    }

    @Override
    public abstract E get(int index);

    @Override
    public abstract E set(int index, E element);

    @Override
    public abstract void add(int index, E element);

    @Override
    public abstract E remove(int index);

    @Override
    public abstract int indexOf(E element);

    @Override
    public abstract void clear();

}
