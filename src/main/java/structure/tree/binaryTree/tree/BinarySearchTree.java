package structure.tree.binaryTree.tree;

import structure.tree.binaryTree.operation.AbstractBinaryTree;
import structure.tree.operation.Comparator;
import util.printer.BinaryTreeInfo;

@SuppressWarnings({"all"})
public class BinarySearchTree<E> extends AbstractBinaryTree<E> implements BinaryTreeInfo {
    public BinarySearchTree(){

    }
    public BinarySearchTree(Comparator<E> comparator){
        super(comparator);
    }

    /**
     * 我是一颗最基础的二叉搜索树，不需要进行添加后的调整
     * @param node 新添加的节点
     */
    @Override
    protected void addAfter(Node<E> node) {
        //啥也不做...
    }

    @Override
    protected void removeAfter(Node<E> node) {
        //啥也不做...
    }

    /**
     *二叉搜索树使用普通节点，返回普通节点
     */
    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new Node<>(element,parent);
    }


    /**
     * 实现树的打印接口（BinaryTreeInfo）
     *
     * @return 根节点
     */
    @Override
    public Object root() {
        return root;
    }

    /**
     * 实现树的打印接口（BinaryTreeInfo）
     *
     * @return 左边节点
     */
    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    /**
     * 实现树的打印接口（BinaryTreeInfo）
     *
     * @return 右边节点
     */
    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    /**
     * 实现树的打印接口（BinaryTreeInfo）
     *
     * @return 将节点转化为字符串返回
     */
    @Override
    public Object string(Object node) {
        String parent = "(null)";
        if (((Node<E>) node).parent != null) {
            parent = "(" + ((Node<E>) node).parent.element.toString() + ")";
        }
        return parent + ((Node<E>) node).element;
    }
}
