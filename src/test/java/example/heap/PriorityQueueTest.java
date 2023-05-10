package example.heap;

import org.junit.Test;
import pojo.Person;
import structure.linear.heap.queue.PriorityQueue;

public class PriorityQueueTest {

    @Test
    public void priorityQueueTest01(){
        //Person 规定年龄越大，优先级越高
        PriorityQueue<Person> priorityQueue = new PriorityQueue<>((e1, e2) -> e2.getAge() - e1.getAge());

        priorityQueue.enQueue(new Person(18, 1.56f, "张三"));
        priorityQueue.enQueue(new Person(23, 1.76f, "李四"));
        priorityQueue.enQueue(new Person(45, 1.87f, "王五"));
        priorityQueue.enQueue(new Person(56, 1.65f, "赵六"));
        priorityQueue.enQueue(new Person(28, 1.74f, "贾倩倩"));
        priorityQueue.enQueue(new Person(18, 1.77f, "花蓉蓉"));

        while (!priorityQueue.isEmpty()){
            Person person = priorityQueue.deQueue();
            System.out.println(person);
        }

    }
}
