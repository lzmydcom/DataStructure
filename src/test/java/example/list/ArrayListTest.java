package example.list;

import org.junit.Assert;
import org.junit.Test;
import structure.linearStructure.array.list.ArrayList;
import structure.linearStructure.array.queue.CircleQueue;
import structure.linearStructure.array.stack.ArrayStack;

public class ArrayListTest {
    @Test
    public void arrayListTest01(){
        //开启日志
        ArrayList.openLog();

        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(13);
        arrayList.add(154);
        arrayList.add(15);
        arrayList.add(14);
        arrayList.add(15);
        arrayList.add(14);
        arrayList.add(12);
        arrayList.add(11);
        arrayList.add(44);
        arrayList.add(33);
        arrayList.add(0,9999);
        arrayList.add(11,11111);
        arrayList.add(14,4444);


        Assert.assertEquals(33,arrayList.get(13));

        arrayList.set(13,8888);

        Assert.assertEquals(8888, arrayList.get(13));

        arrayList.add(8793);

        Assert.assertEquals(2,arrayList.remove(2));

        System.out.println(arrayList);
    }

    @Test
    public void stackTest(){
        ArrayStack<Object> stack = new ArrayStack<>();
        stack.push("zs");
        stack.push("ls");
        stack.push(999);
        int size = stack.size();
        for (int i = 0; i < size; i++){
            System.out.println(stack.pop());
        }
    }

    @Test
    public void CircleQueueTest(){
        CircleQueue<Object> queue = new CircleQueue<>();
        queue.enQueue("你好！！！");
        queue.enQueue("hello！！！");
        queue.enQueue("happy！！！");
        queue.enQueue("very day！！！");

        System.out.println(queue.deQueue());
        System.out.println(queue.deQueue());

        queue.enQueue("33！！！");
        queue.enQueue("55！！！");
        queue.enQueue("66 day！！！");
        queue.enQueue("77！！！");
        queue.enQueue("88！！！");
        queue.enQueue("99 day！！！");
        queue.deQueue();
        queue.deQueue();
        queue.deQueue();
        queue.deQueue();
        queue.deQueue();
        queue.deQueue();

        queue.enQueue("33！！！");
        queue.enQueue("55！！！");
        queue.enQueue("66 day！！！");
        queue.enQueue("77！！！");
        queue.enQueue("88！！！");
        queue.enQueue("99 day！！！");

        queue.enQueue("小甜甜！！！");
        queue.enQueue("树英姐姐！！！");
        queue.enQueue("扩容辣嘛？");
        int size = queue.size();

        for (int i = 0; i < size; i++){
            if (i == size - 1){
                System.out.println(queue.deQueue());
                return;
            }
            System.out.println(queue.deQueue());
        }
    }
}
