package structure.treeStructure.operation;

public abstract class Visitor<E> {
    public static boolean stop = false;
    public abstract void visit(E e);
}
