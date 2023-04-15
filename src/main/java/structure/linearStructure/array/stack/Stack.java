package structure.linearStructure.array.stack;

public interface Stack<E> {
    int size();
    boolean isEmpty();
    void push(E element);
    E pop();
    E top();
}
