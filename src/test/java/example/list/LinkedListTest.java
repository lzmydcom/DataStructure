package example.list;

import org.junit.Assert;
import org.junit.Test;
import structure.linearStructure.linked.list.LinkedList;


public class LinkedListTest {
    @Test
    public void simpleLinkedListTest(){
        LinkedList<Object> list = new LinkedList<>();
        list.add(12);
        list.add(44);
        list.add(null);
        list.add(1,"hello");
        list.add(89.22);
        Assert.assertEquals(3, list.indexOf(null));

        System.out.println(list);

        Assert.assertNull(list.remove(3));

        System.out.println(list);

        Assert.assertEquals(44,list.remove(2));

        System.out.println(list);

        Assert.assertEquals(0, list.indexOf(12));
        Assert.assertEquals(1, list.indexOf("hello"));
        Assert.assertEquals(2, list.indexOf(89.22));
    }

    @Test
    public void reverseListTest(){
        /*list.List head = new list.List();*/
        LinkedList<Object> head = new LinkedList<>();
        head.add(1);
        head.add(2);
        head.add(3);
        head.add(4);
        head.add(5);

        System.out.println(head);
        head.reverseList();
        System.out.println(head);
    }


}

