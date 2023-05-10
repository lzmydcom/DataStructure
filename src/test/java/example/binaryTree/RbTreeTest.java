package example.binaryTree;

import org.junit.Test;
import structure.tree.binaryTree.tree.RedBlackTree;
import util.printer.BinaryTrees;

public class RbTreeTest {
    @Test
    public void addTest() {
        //60,1,55
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        RedBlackTree<Integer> tree = new RedBlackTree<>((e1, e2) -> e1 - e2);
        for (Integer integer : integers) {
            tree.add(integer);
        }

        BinaryTrees.println(tree);
        System.out.println(tree.size());
    }

    @Test
    public void bstRemoveTest(){
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};

        RedBlackTree<Integer> tree = new RedBlackTree<>((e1, e2) -> e1 - e2);
        for (Integer integer : integers) {
            tree.add(integer);
        }

        BinaryTrees.println(tree);
        for (Integer integer:integers){
            if (integer == 67){
                tree.remove(integer);
            }else {
                tree.remove(integer);
            }

            BinaryTrees.println(tree);
            System.out.println(integer + "   " + tree.size() + "==========================================================================================================");
        }
    }
   /* @Override
    protected void removeAfter(AbstractBinaryTree.Node<E> node, AbstractBinaryTree.Node<E> replacement) {

        说明:
        在二叉树中元素的删除都是发生在度为1节点或0的节点中，度为2的节点是找到它的前驱节点或后继节点删除
        而在2-3-4树中，度为1或0的节点都在 ”超级叶子节点” 中（红黑树等价于2-3-4树）

        //被删除的元素是红色，不做任何处理
        if (isRed(node)) return;
        //能来到这说明被删元素是黑色，且被代替元素是红色
        if (isRed(replacement)) {
            black(replacement);
            return;
        }
        //能来到这，说明被删元素是黑色叶子节点
        AbstractBinaryTree.Node<E> parent = node.parent;
        //删除的是根节点
        if (parent == null) return;

        //判断被删元素是左边还是在右边
        boolean direction = parent.left == null;
        RedBlackTree.RbNode<E> sibling = downcast(node).sibling();
        //被删元素在左边
        if (direction){

        }
        //被删元素在右边
        else{
            if (isRed(sibling)){
                black(sibling);
                red(parent);
                rightRotation(parent);
                //更换兄弟
                sibling = (RedBlackTree.RbNode<E>) parent.left;
            }
        }
    }
*/
}
