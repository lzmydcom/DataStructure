package example.binaryTree;

import org.junit.Test;
import pojo.Student;
import structure.tree.binaryTree.tree.BinarySearchTree;
import structure.tree.operation.Comparator;
import util.printer.BinaryTrees;

public class BinarySearchTreeTest {
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
    public void binarySearchTreeTest(){
        Integer[] args = {23,32,42,1213,321,1,654,53,765,87,86,24,644,442};

        //因为我的二叉搜索树的实现中是要求元素实现我自己写的的Comparable接口，Integer也实现了JDK 的Comparable接口，但
        //没有实现我的 Comparable接口，所以使用我实现的BinarySearchTree存入Integer元素时必须要传入一个比较器 ！！！

        BinarySearchTree<Object> integerBinarySearchTree = new BinarySearchTree<>(new Comparator<Object>() {

            @Override
            public int compare(Object e1, Object e2) {
               if (e1 instanceof Integer){
                   if (e2 instanceof Integer){
                       return ((Integer)e1) - ((Integer)e2);
                   }else {
                       return (Integer)e1 - ((Student)e2).getAge();
                   }
               } else if (e2 instanceof Integer) {
                   return ((Student)e1).getAge() - (Integer) e2;
               }else {
                   return  ((Student)e1).getAge() -  ((Student)e2).getAge();
               }
            }
        });
        for (Integer i:args){
            integerBinarySearchTree.add(i);
        }
        integerBinarySearchTree.add(new Student("zs", 28));
        integerBinarySearchTree.add(new Student("li", 23));
        integerBinarySearchTree.add(new Student("ww", 12));
        integerBinarySearchTree.add(new Student("zl", 32));

        integerBinarySearchTree.add(new Student("ll",32));
        BinaryTrees.println(integerBinarySearchTree);
    }

    @Test
    public void removeTest(){
        BinarySearchTree<Integer> binarySearchTree = common();
        //删除一个度为1的节点
        //有一个左孩子
        binarySearchTree.remove(442);
        //有一个右孩子
        binarySearchTree.remove(765);
        System.out.println("========================================\n");
        BinaryTrees.println(binarySearchTree);

        //删除叶子结点
        binarySearchTree.remove(87);
        binarySearchTree.remove(321);
        binarySearchTree.remove(42);

        System.out.println("========================================\n");
        BinaryTrees.println(binarySearchTree);
        //删除度为2的节点
        binarySearchTree.remove(24);
        binarySearchTree.remove(53);
        System.out.println("========================================\n");
        BinaryTrees.println(binarySearchTree);
    }

}
