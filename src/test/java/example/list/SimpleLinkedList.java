package example.list;

public class SimpleLinkedList {
    ListNode first;

    static class ListNode {
        int val;
        ListNode next;
        ListNode(int val){
            this.val = val;
        }
    }

    void add(int val){
        ListNode node = first;
        if (first == null){
            first = new ListNode(val);
        }else {
            while (node.next != null){
                node = node.next;
            }
            node.next = new ListNode(val);
        }
    }

    @Override
    public String toString() {
        ListNode p = first;

        if (first == null) return "[]";

        StringBuilder stringBuilder = new StringBuilder("[");
        while (p != null){
            stringBuilder.append(p.val).append(",");
            p = p.next;
        }
        stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public void reverseList(){
        //如果链表只有一个元素，直接返回头指针
        if (first.next == null) return;
        //将头指针保存一份到point2
        ListNode point1 = first;
        ListNode point2 = first;
        //指向链表第二个元素
        point1 = point1.next;
        //将头指针的next设为空
        point2.next = null;

        ListNode temp;

        while(point1.next != null){
            temp = point1.next;
            point1.next = point2;
            point2 = point1;
            point1 = temp;
        }
        point1.next = point2;
        this.first = point1;
    }
}