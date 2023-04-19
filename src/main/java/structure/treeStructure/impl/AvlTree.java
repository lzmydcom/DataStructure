package structure.treeStructure.impl;

import structure.treeStructure.operation.AbstractBalancedBinarySearchTree;
import structure.treeStructure.operation.Comparator;
import util.printer.BinaryTreeInfo;

@SuppressWarnings("all")
public class AvlTree<E> extends AbstractBalancedBinarySearchTree<E> implements BinaryTreeInfo {

    public AvlTree() {

    }

    public AvlTree(Comparator<E> comparator) {
        super(comparator);
    }



    private static class AvlNode<E> extends Node<E> {
        protected int height = 1;

        public AvlNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AvlNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AvlNode<E>) right).height;
            return leftHeight - rightHeight;
        }
    }

    @Override
    protected void addAfter(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced((AvlNode<E>) node)) {
                //平衡，更新高度
                updateHeight((AvlNode<E>) node);
            } else {
                //不平衡，进行旋转调整，恢复平衡
                reBalance(node);
                break;
            }
        }
    }

    @Override
    protected void removeAfter(Node<E> node) {
        while ((node = node.parent) != null) {
            if (isBalanced((AvlNode<E>) node)) {
               updateHeight((AvlNode<E>) node);
            }else {
                //不平衡，进行旋转调整，恢复平衡
                //由于进行的是删除，所以有可能导致被删除的节点所在的整棵子树高度减一，进而导致更上层节点高度减一
                reBalance(node);
            }
        }
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new AvlNode<>(element, parent);
    }




    /**
     * 判断一个节点是否处于平衡状态
     *
     * @param avlNode 传入节点
     */
    private boolean isBalanced(AvlNode<E> avlNode) {
        return Math.abs(avlNode.balanceFactor()) <= 1;
    }

    private void updateHeight(AvlNode<E> avlNode) {
        int rightHeight = avlNode.right == null ? 0 : ((AvlNode<E>) avlNode.right).height;
        int leftHeight = avlNode.left == null ? 0 : ((AvlNode<E>) avlNode.left).height;
        avlNode.height = Math.max(rightHeight, leftHeight) + 1;
    }

    private void rightRotation(Node<E> grand,Node<E> parent){
        rightRotation(grand);
        //更新高度
        updateHeight((AvlTree.AvlNode<E>) parent);
        updateHeight((AvlTree.AvlNode<E>) grand);
    }

    private void leftRotation(Node<E> grand,Node<E> parent){
        leftRotation(grand);
        //更新高度
        updateHeight((AvlTree.AvlNode<E>) parent);
        updateHeight((AvlTree.AvlNode<E>) grand);
    }

    /**
     * 进行平衡恢复
     *
     * @param grand 最底层最先不平衡的那个节点
     */
    private void reBalance(Node<E> grand) {
        Node<E> parent = tallerNode(grand);
        Node<E> eNode = tallerNode(parent);
        if (parent.isLeftOfTheFather()) {
            if (eNode.isRightOfTheFather()) {
                //LR
                leftRotation(parent,eNode);
            }   //LL
            rightRotation(grand,parent);
        } else {
            if (eNode.isLeftOfTheFather()) {
                //RL
                rightRotation(parent,eNode);

            }  //RR
            leftRotation(grand,parent);
        }
    }

    private Node<E> tallerNode(Node<E> eNode) {
        int rightHeight = eNode.right == null ? 0 : ((AvlNode<E>) eNode.right).height;
        int leftHeight = eNode.left == null ? 0 : ((AvlNode<E>) eNode.left).height;
        return rightHeight > leftHeight ? eNode.right : eNode.left;
    }

    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((AvlNode<E>)node).left;
    }

    @Override
    public Object right(Object node) {
        return ((AvlNode<E>)node).right;
    }

    @Override
    public Object string(Object node) {
        String o = ((AvlNode<E>) node).parent == null ? "(null)" : "(" + ((AvlNode<E>) node).parent.element + ")";
        return ((AvlNode<E>)node).element + o;
    }
}
