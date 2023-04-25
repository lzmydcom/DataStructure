package example.binaryTree;

import org.junit.Test;
import structure.treeStructure.binaryTree.tree.BinarySearchTree;
import structure.treeStructure.operation.Comparator;
import structure.treeStructure.operation.Visitor;
import util.printer.BinaryTrees;

public class BinaryTreeTest {

    public BinarySearchTree<Integer> common(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer e1, Integer e2) {
                return e1 - e2;
            }
        });
        Integer[] args = {654,53,765,87,86,24,644,442,23,32,42,1213,321};
        for (Integer i:args){
            binarySearchTree.add(i);
        }

        BinaryTrees.println(binarySearchTree);
        return binarySearchTree;
    }
    @Test
    public void inorderTraversalTest(){
        BinarySearchTree<Integer> binarySearchTree = common();
        binarySearchTree.inorderTraversal(new Visitor<Integer>() {
            @Override
            public void visit(Integer integer) {
                System.out.println(integer);
                //中序遍历，遍历到53时结束整个递归
                if (integer == 53){
                    stop = true;
                }
            }
        });
    }

    @Test
    public void level_order_traversalTest(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer e1, Integer e2) {
                return e1 - e2;
            }
        });
        Integer[] args = {654,53,765,87,86,24,644,442,23,32,42,1213,321};

        for (Integer i:args){
            binarySearchTree.add(i);
        }

        BinaryTrees.println(binarySearchTree);
        binarySearchTree.level_order_traversal(new Visitor<Integer>() {
            @Override
            public void visit(Integer integer) {
                System.out.println(integer);
                //层序遍历，遍历到87时结束整个递归
                if (integer == 87){
                    stop = true;
                }
            }
        });
    }

    @Test
    public void treeHeightTest(){
        BinarySearchTree<Integer> binarySearchTree = common();
        BinaryTrees.println(binarySearchTree);
        //递归
        System.out.println(binarySearchTree.treeHeight(false));
        //队列层序遍历
        System.out.println(binarySearchTree.treeHeight(true));
    }

    @Test
    public void isCompleteBinaryTreeTest(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer e1, Integer e2) {
                return e1 - e2;
            }
        });
        Integer[] args = {53,42,61,33,44,55,67}; //true
       /*
        //Integer[] args = {53,42,61,33,44,55,67,22}; //true
        //Integer[] args = {53,42,61,33,44,67,22}; //false
        //Integer[] args = {53,42,61,33,44,55,67,22,11,43}; //false
        //Integer[] args = {53,42,61,33,44,55,67,22,43}; //false
        //Integer[] args = {53,42,61,33,44,55,67,22,43,34}; //true
        //Integer[] args = {53,42,61,33,44,67}; //false
        */

        for (Integer i:args){
            binarySearchTree.add(i);
        }
        BinaryTrees.println(binarySearchTree);

        System.out.println(binarySearchTree.isCompleteBinaryTree());
    }


    @Test
    public void flipBinaryTreeTest(){
        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer e1, Integer e2) {
                return e1 - e2;
            }
        });
        Integer[] args = {654,53,765,87,86,24,644,442,23,32,42,1213,321};

        for (Integer i:args){
            binarySearchTree.add(i);
        }
        BinaryTrees.println(binarySearchTree);
        binarySearchTree.flipBinaryTree();
        BinaryTrees.println(binarySearchTree);
    }

}
