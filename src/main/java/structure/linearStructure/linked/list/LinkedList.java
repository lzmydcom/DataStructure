package structure.linearStructure.linked.list;

import structure.linearStructure.operation.AbstractList;

public class LinkedList<E> extends AbstractList<E> {

    private Node<E> first;

    private static class Node<E> {
        E element;
        private Node<E> next;

        public Node(E element, Node<E> next) {
            this.element = element;
            this.next = next;
        }
    }

    @Override
    public E get(int index) {
        indexCheck(index);
        Node<E> eNode = pointerToIndex(index);
        return eNode.element;
    }

    @Override
    public E set(int index, E element) {
        indexCheck(index);
        Node<E> eNode = pointerToIndex(index);
        E oldElement = eNode.element;
        eNode.element = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        indexCheckForAdd(index);

        if (first == null || index == 0) {
            first = new Node<>(element, first);
        }else {
            Node<E> eNode = pointerToIndex(index - 1);
            Node<E> temp = eNode.next;
            eNode.next = new Node<>(element, temp);
        }
        size++;
    }


    @Override
    public E remove(int index) {
        indexCheck(index);
        Node<E> temp;
        if (index == 0){
            temp = first;
            first = null;
        }else {
            Node<E> eNode = pointerToIndex(index - 1);
            temp = eNode.next;
            eNode.next = eNode.next.next;
        }
        size--;
        return temp.element;
    }

    @Override
    public int indexOf(E element) {
        Node<E> head = first;
        if (element == null) {
            for (int i = 0; i < size; i++) {
                if (head.element == null) {
                    return i;
                }
                head = head.next;
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (element.equals(head.element)) {
                    return i;
                }
                head = head.next;
            }
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        first = null;
        size = 0;
    }

    private Node<E> pointerToIndex(int index) {
        Node<E> head = first;
        for (int i = 0; i < index; i++) {
            head = head.next;
        }
        return head;
    }

    public void reverseList(){
        //如果链表只有一个元素，直接返回头指针
        if (first.next == null) return;
        //将头指针保存一份到point2
        Node<E> point1 = first;
        Node<E> point2 = first;
        //指向链表第二个元素
        point1 = point1.next;
        //将头指针的next设为空
        point2.next = null;

        Node<E> temp;

        while(point1.next != null){
            temp = point1.next;
            point1.next = point2;
            point2 = point1;
            point1 = temp;
        }
        point1.next = point2;
        this.first = point1;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("[");
        Node<E> head = first;
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                stringBuilder.append(head.element).append(",");
            } else {
                stringBuilder.append(head.element).append("]");
            }
            head = head.next;
        }
        return "LinkedList{" +
                "size=" + size +
                ", first=" + stringBuilder +
                '}';
    }
}
