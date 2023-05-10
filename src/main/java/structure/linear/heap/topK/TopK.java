package structure.linear.heap.topK;

import structure.linear.heap.binaryHeap.SmallTopHeap;
import util.printer.BinaryTrees;

public class TopK {
    //从超大数据量中中选出 K 个最大的数据
    public static void main(String[] args) {
        //从一千万数据中选出最大的100个数据
        Long[] longs = new Long[10000000];


        for (int i = 0; i < 10000000; i++){
            //1-1000000
            longs[i] = (long) (Math.random() * 1000001);
        }
        //创建小顶堆
        SmallTopHeap<Long> smallTopHeap = new SmallTopHeap<>();
        for (Long l:longs){
            //堆中元素个数还没达到 100 , size 从 0 开始计数
            if (smallTopHeap.size() < 100){
                smallTopHeap.add(l);
            }
            //小顶堆中元素达到100，如果元素大于堆顶元素，将该元素与堆顶元素替换
            else {
              if (smallTopHeap.get() < l){
                  smallTopHeap.replace(l);
              }
            }
        }

        BinaryTrees.println(smallTopHeap);
        System.out.println("小顶堆中元素个数 size:【" + smallTopHeap.size() + "】");
    }
}
