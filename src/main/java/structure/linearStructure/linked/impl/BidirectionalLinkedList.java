package structure.linearStructure.linked.impl;

import structure.linearStructure.operation.AbstractList;

import java.util.Objects;

@SuppressWarnings({"all"})
public class BidirectionalLinkedList<E> extends AbstractList<E> {
    private Node<E> first;
    private Node<E> last;

    private static class Node<E> {
        E element;
        private Node<E> next;
        private Node<E> prev;

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
        if (index == 0) {
            insertFirst(element);
        } else if (index == size) {
            insertLast(element);
        } else {
            Node<E> prevNode = pointerToIndex(index - 1);
            Node<E> insertNode = new Node<>(element, prevNode, prevNode.next);
            prevNode.next.prev = insertNode;
            prevNode.next = insertNode;
        }
        size++;
    }

    @Override
    public E remove(int index) {
        indexCheck(index);
        E oldElement;
        if (index == 0) oldElement = removeFirst();
        else if (index == size - 1) oldElement = removeLast();
        else {
            Node<E> eNode = pointerToIndex(index - 1);
            Node<E> oldNode = eNode.next;
            oldNode.next.prev = eNode;
            eNode.next = oldNode.next;
            oldElement = oldNode.element;
        }
        size--;
        return oldElement;
    }

    private E removeLast() {
        E oldElement = last.element;
        last.prev.next = null;
        last = last.prev;
        return oldElement;
    }

    private E removeFirst() {
        E oldElement;
        if (size == 1) {
            oldElement = first.element;
            first = null;
            return oldElement;
        }
        oldElement = first.element;
        first.next.prev = null;
        first = first.next;
        return oldElement;
    }

    @Override
    public int indexOf(E element) {
        Node<E> head = first;
        for (int i = 0; i < size; i++) {
            if (Objects.equals(head.element, element)) {
                return i;
            }
            head = head.next;
        }

        return ELEMENT_NOT_FOUND;
    }

    @Override
    public void clear() {
        size = 0;
        first = null;
        last = null;
    }

    private Node<E> pointerToIndex(int index) {
        Node<E> head;
        if (index <= (size >> 1)) {
            head = first;
            for (int i = 0; i < index; i++) {
                head = head.next;
            }
        } else {
            head = last;
            index = size - index - 1;
            for (int i = 0; i < index; i++) {
                head = head.prev;
            }
        }
        return head;
    }

    private void insertFirst(E element) {
        if (first == null) {
            first = new Node<>(element, null, null);
            last = first;
            return;
        }
        Node<E> eNode = new Node<>(element, null, first);
        first.prev = eNode;
        first = eNode;
    }

    private void insertLast(E element) {
        Node<E> eNode = new Node<>(element, last, null);
        last.next = eNode;
        last = eNode;
    }

    @Override
    public String toString() {
        StringBuilder builderFirst = new StringBuilder("[");
        StringBuilder builderLast = new StringBuilder("[");
        Node<E> head = first;
        for (int i = 0; i < size; i++) {
            if (head.element == null) {
                builderFirst.append("null");
            } else {
                builderFirst.append(head.element);
            }
            builderFirst.append("-->");
            head = head.next;
        }
        Node<E> tail = last;
        for (int i = 0; i < size; i++) {
            if (tail.element == null) {
                builderLast.append("null");
            } else {
                builderLast.append(tail.element);
            }
            builderLast.append("-->");
            tail = tail.prev;
        }
        return "BidirectionalLinkedList {\n" +
                "size=" + size +
                ", \nfirst=" + builderFirst +
                "], \nlast =" + builderLast +
                "]\n}";
    }
}
