package structure.treeStructure.heap.binaryHeap;

import structure.treeStructure.heap.operation.AbstractBinaryHeap;
import structure.treeStructure.operation.Comparator;

@SuppressWarnings("all")
public class SmallTopHeap<E> extends AbstractBinaryHeap<E> {
    public SmallTopHeap() {
        super();
    }

    public SmallTopHeap(Comparator<E> comparator) {
        super(comparator);
    }


    @Override
    protected void siftUp(int index) {
        E element = elements[index];
        while (index > 0){
            int parentIndex = (index - 1) >> 1;
            int newIndex = index;
            int cmp = compare(element, elements[parentIndex]);
            if (cmp < 0){
                elements[newIndex] = elements[parentIndex];
                index = (index - 1) >> 1;
            }else {
                break;
            }
        }
        elements[index] = element;
    }

    @Override
    protected void siftDown(int index) {
        E element = elements[index];
        int leftIndex = (index << 1) + 1;
        int rightIndex = leftIndex + 1;
        //一定有子节点
        while (leftIndex < size) {
            E elementL = elements[leftIndex];
            if (rightIndex < size) {
                E elementR = elements[rightIndex];
                if (compare(elementL, elementR) < 0) {
                    if (compare(element, elementL) > 0){
                        elements[index] = elementL;
                        index = leftIndex;
                    }else {
                        break;
                    }
                }else {
                    if (compare(element, elementR) > 0){
                        elements[index] = elementR;
                        index = rightIndex;
                    }else {
                        break;
                    }
                }
            } else{
                if (compare(element, elementL) > 0){
                    elements[index] = elementL;
                    index = leftIndex;
                }else {
                    break;
                }
            }
            leftIndex = (index << 1) + 1;
            rightIndex = leftIndex + 1;
        }
        elements[index] = element;
    }
}
