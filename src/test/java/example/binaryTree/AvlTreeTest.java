package example.binaryTree;

import org.junit.Test;
import structure.tree.binaryTree.tree.AvlTree;
import util.printer.BinaryTrees;

import java.util.Arrays;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class AvlTreeTest {
    @Test
    public void addTest(){
        AvlTree<Integer> binaryTree = new AvlTree<>((e1, e2) -> e1 - e2);
        Integer[] integers = {85,19,69,3,7,99,95,2,1,70,44,58,11,21,14,93,57,4,56};
        for (Integer integer:integers){
            binaryTree.add(integer);
        }
        BinaryTrees.println(binaryTree);
    }

    @Test
    public void removeTest(){
        AvlTree<Integer> binaryTree = new AvlTree<>((e1, e2) -> e1 - e2);
        Integer[] integers = {85,19,69,3,7,99,95,2,1,70,44,58,11,21,14,93,57,4,56};
        for (Integer integer:integers){
            binaryTree.add(integer);
        }

        binaryTree.add(10);
        binaryTree.add(15);
        binaryTree.add(13);
        binaryTree.add(16);
        binaryTree.add(22);
        binaryTree.add(59);
        BinaryTrees.println(binaryTree);
        System.out.println("===========================================================");

        binaryTree.remove(93);
        BinaryTrees.println(binaryTree);
        System.out.println("===========================================================");

        binaryTree.remove(99);
        BinaryTrees.println(binaryTree);
        System.out.println(binaryTree.size());
        List<Integer> list = Arrays.stream(integers).filter(integer -> integer != 99 && integer != 93).collect(Collectors.toList());
        list.add(10);
        list.add(15);
        list.add(13);
        list.add(16);
        list.add(22);
        list.add(59);
        System.out.println(list.size());
        for (Integer integer: list){
            binaryTree.remove(integer);
        }
        new TreeMap<>();
        System.out.println(binaryTree.size());
    }

    @Test
    public void removeTest01(){
        Integer[] integers = {60, 1, 55, 96, 5, 78, 62, 90, 20,
                72, 67, 64, 28, 95, 27, 3, 19, 33, 68, 41, 94,
                79, 38, 7, 24, 80, 9, 13, 4, 40, 11, 58, 99, 16,
                93, 71, 46, 65, 74, 76, 44, 47, 15};
        AvlTree<Integer> bbs = new AvlTree<>((e1, e2) -> e1-e2);
        for (Integer integer: integers){
            bbs.add(integer);
        }
        for (Integer integer: integers){
            if (integer == 19){
                bbs.remove(integer);
            }else {
                bbs.remove(integer);
            }
            BinaryTrees.println(bbs);
            System.out.println(integer);
        }
    }
}
