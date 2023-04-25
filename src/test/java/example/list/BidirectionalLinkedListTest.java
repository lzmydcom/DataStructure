package example.list;

import org.junit.Assert;
import org.junit.Test;
import structure.linearStructure.linked.list.BidirectionalLinkedList;
import structure.linearStructure.linked.list.CircularLinkedList;
import structure.linearStructure.linked.queue.BidirectionalLinkedQueue;
import structure.linearStructure.operation.Deque;
import structure.linearStructure.linked.queue.LinkedQueue;
import structure.linearStructure.operation.List;

public class BidirectionalLinkedListTest {
    @Test
    public void bidirectionalLinkedListTest(){
        List<Object> list = new BidirectionalLinkedList<>();
        list.add(7);
        list.add(799);
        list.add(0,"你好");
        list.add(7737);
        list.add("chatGPT4");
        list.add(5,"AI改变世界");

        list.add(4,83838);
        list.add(2,4332);
        list.add(7,"武大郎");
        list.add(null);
        list.add(3,null);

        Assert.assertEquals(3,list.indexOf(null));
        Assert.assertEquals(8, list.indexOf("武大郎"));
        Assert.assertEquals("武大郎",list.remove(8));
        Assert.assertEquals("你好",list.remove(0));
        Assert.assertNull(list.remove(8));
        System.out.println(list);

        /*CircularLinkedList: [ 7, 4332, null, 799, 7737, 83838, chatGPT4, AI改变世界 ]*/
    }

    @Test
    public void queueTest(){
        LinkedQueue<Object> queue = new LinkedQueue<>();
        queue.enQueue(321);
        queue.enQueue(32);
        queue.enQueue("hello");
        queue.enQueue(99999);
        queue.enQueue("你好呀！！！");
        int size = queue.size();
        for (int i = 0; i < size; i++){
            System.out.println(queue.deQueue());
        }

    }

    @Test
    public void dequeTest(){
        Deque<Object> queue = new BidirectionalLinkedQueue<Object>();
        queue.enQueueFront(321);
        queue.enQueueRear(32);
        queue.enQueueRear("hello");
        queue.enQueueFront(99999);
        queue.enQueueRear("你好呀！！！");
        int size = queue.size();
        //头99999 321 32 hello 你好呀！！！尾

        for (int i = 0; i < size; i++){
            System.out.println(queue.deQueueRear());
        }
        queue.enQueueFront(321);
        queue.enQueueRear(32);
        queue.enQueueRear("hello");
        queue.enQueueFront(99999);
        queue.enQueueRear("你好呀！！！");
        size = queue.size();

        System.out.println("==============");
        for (int i = 0; i < size; i++){
            System.out.println(queue.deQueueFront());
        }

    }

    @Test
    public void circularLinkedListTest(){
        CircularLinkedList<Object> list = new CircularLinkedList<>();
        list.add(7);
        list.add(799);
        list.add(0,"你好");
        list.add(7737);
        list.add("chatGPT4");
        list.add(5,"AI改变世界");

        list.add(4,83838);
        list.add(2,4332);
        list.add(7,"武大郎");
        list.add(null);
        list.add(3,null);

        Assert.assertEquals(3,list.indexOf(null));
        Assert.assertEquals(8, list.indexOf("武大郎"));
        Assert.assertEquals("武大郎",list.remove(8));
        Assert.assertEquals("你好",list.remove(0));
        Assert.assertNull(list.remove(8));
        System.out.println(list);
        System.out.println(list.toString(true));
    }

}
