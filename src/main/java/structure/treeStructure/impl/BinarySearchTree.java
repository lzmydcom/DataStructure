package structure.treeStructure.impl;

import structure.treeStructure.BinaryTree;
import structure.treeStructure.Comparable;
import structure.treeStructure.Comparator;
import structure.treeStructure.Visitor;
import util.printer.BinaryTreeInfo;

@SuppressWarnings({"unchecked"})
public class BinarySearchTree<E> implements BinaryTree<E>, BinaryTreeInfo {
    private int size;

    private Node<E> root;

    private Comparator<E> comparator;

    public BinarySearchTree() {
    }

    public BinarySearchTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    private static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.parent = parent;
            this.element = element;
        }
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
    public void clear() {

    }

    @Override
    public void add(E element) {
        //添加根节点
        if (root == null) {
            root = new Node<>(element, null);
            size++;
            return;
        }
        //添加的不是根节点
        int cmp = 0;
        Node<E> node = root;
        Node<E> parent = null;
        while (node != null) {
            cmp = compare(element, node.element);
            //找到新添加元素的父节点
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }
        //判断新元素加在父节点的哪个位置
        if (cmp > 0) {
            parent.right = new Node<>(element, parent);
        } else {
            parent.left = new Node<>(element, parent);
        }
        size++;
    }

    @Override
    public void remove(E element) {

    }

    @Override
    public boolean contains(E element) {
        return false;
    }

    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must be not null");
        }
    }

    public void inorderTraversal(Visitor<E> visitor) {
        inorderTraversal(root, visitor);
    }

    private void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null) return;
        inorderTraversal(node.left, visitor);
        visitor.visit(node.element);
        inorderTraversal(node.right, visitor);
    }

    /**
     * @param e1 比较参数e1
     * @param e2 比较参数e2
     * @return 返回值等于0，代表e1等于e2，返回值大于0，e1大于e2，返回值小于0，e1小于e2
     */
    private int compare(E e1, E e2) {
        return comparator == null ? ((Comparable<E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        String parent = "(null)";
        if (((Node<E>) node).parent != null) {
            parent = "(" + ((Node<E>) node).parent.element.toString() + ")";
        }
        return parent + ((Node<E>) node).element;
    }
}
