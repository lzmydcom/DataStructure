package structure.linear.linked.list;

import structure.linear.operation.AbstractList;

import java.util.Objects;

@SuppressWarnings({"all"})
public class CircularLinkedList<E> extends AbstractList<E> {

    private Node<E> head;

    private static class Node<E> {
        E element;
        private Node<E> prev;
        private Node<E> next;

        public Node(E element, Node<E> prev, Node<E> next) {
            this.element = element;
            this.prev = prev;
            this.next = next;
        }
    }


    @Override
    public E get(int index) {
        return pointerToIndex(index).element;
    }

    @Override
    public E set(int index, E element) {
        Node<E> eNode = pointerToIndex(index);
        E oldElement = eNode.element;
        eNode.element = element;
        return oldElement;
    }

    @Override
    public void add(int index, E element) {
        indexCheckForAdd(index);
        if (size == 0) {
            insertFirstElement(element);
        } else {
            boolean flag = false;
            if (index == 0){
                index = size;
                flag = true;
            }
            Node<E> prevNode = pointerToIndex(index - 1);
            Node<E> insertNode = new Node<>(element, prevNode, prevNode.next);
            prevNode.next.prev = insertNode;
            prevNode.next = insertNode;
            if (flag){
                head = insertNode;
            }
        }
        size++;
    }

    private Node<E> pointerToIndex(int index) {
        Node<E> headPointer = head;
        if (index <= (size >> 1)) {
            for (int i = 0; i < index; i++) {
                headPointer = headPointer.next;
            }
        } else {
            index = size - index;
            for (int i = 0; i < index; i++) {
                headPointer = headPointer.prev;
            }
        }
        return headPointer;
    }

    private void insertFirstElement(E element) {
        head = new Node<>(element, null, null);
        head.prev = head;
        head.next = head;
    }

    @Override
    public E remove(int index) {
        indexCheck(index);
        E oldElement;
        if (size == 1) oldElement = removeOnlyElement();
        else {
            boolean flag = false;
            if (index == 0){
                index = size;
                flag = true;
            }
            Node<E> eNode = pointerToIndex(index - 1);
            Node<E> oldNode = eNode.next;
            oldNode.next.prev = eNode;
            eNode.next = oldNode.next;
            oldElement = oldNode.element;
            if (flag){
                head = oldNode.next;
            }
        }
        size--;
        return oldElement;
    }

    private E removeOnlyElement() {
        E oldElement = head.element;
        head = null;
        return oldElement;
    }

    @Override
    public int indexOf(E element) {
        Node<E> headPointer = head;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(headPointer.element, element)) {
                return i;
            }
            headPointer = headPointer.next;
        }
        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    public String toString(boolean flag){
        StringBuilder sb = new StringBuilder("CircularLinkedList: next [ ");
        Node<E> node = head;
        for (int i = 0; i < size; i++){
            if (i == size - 1){
                sb.append(node.element).append(" ]");
            }else sb.append(node.element).append(", ");
            node = node.next;
        }
        sb.append(" prev [ ");
        for (int i = 0; i < size; i++){
            if (i == size - 1){
                sb.append(node.element).append(" ]");
            }else sb.append(node.element).append(", ");
            node = node.prev;
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("CircularLinkedList: [ ");
        Node<E> node = head;
        for (int i = 0; i < size; i++){
            if (i == size - 1){
                sb.append(node.element).append(" ]");
            }else sb.append(node.element).append(", ");
            node = node.next;
        }
        return sb.toString();
    }
}
