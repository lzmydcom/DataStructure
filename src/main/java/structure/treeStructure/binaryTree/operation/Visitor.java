package structure.treeStructure.binaryTree.operation;

public abstract class Visitor<E> {
    public static boolean stop = false;
    public abstract void visit(E e);
}