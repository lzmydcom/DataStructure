package structure.treeStructure.operation;

import structure.treeStructure.impl.AvlTree;

public abstract class AbstractBalancedBinarySearchTree <E> extends AbstractBinaryTree<E> {
    public AbstractBalancedBinarySearchTree() {
    }

    public AbstractBalancedBinarySearchTree(Comparator<E> comparator) {
        super(comparator);
    }
    /**
     *
     * @param grand 右旋转
     */
    protected void rightRotation(Node<E> grand) {
        Node<E> parent = grand.left;
        grand.left = parent.right;
        if (parent.right != null) {
            parent.right.parent = grand;
        }
        if (grand.parent == null){
            root = parent;
        } else if (grand.isLeftOfTheFather()){
            grand.parent.left = parent;
        }else {
            grand.parent.right = parent;
        }
        parent.right = grand;
        parent.parent = grand.parent;
        grand.parent = parent;
    }

    /**
     *
     * @param grand 左旋转
     */
    protected void leftRotation(Node<E> grand) {
        Node<E> parent = grand.right;
        grand.right = parent.left;
        if (parent.left != null) {
            parent.left.parent = grand;
        }
        if (grand.parent == null){
            root = parent;
        } else if (grand.isLeftOfTheFather()){
            grand.parent.left = parent;
        }else {
            grand.parent.right = parent;
        }
        parent.left = grand;
        parent.parent = grand.parent;
        grand.parent = parent;
    }

}
