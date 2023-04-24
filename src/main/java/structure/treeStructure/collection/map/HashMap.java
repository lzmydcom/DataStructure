package structure.treeStructure.collection.map;

import structure.linearStructure.array.stack.ArrayStack;
import structure.linearStructure.array.stack.Stack;
import structure.linearStructure.linked.queue.LinkedQueue;
import structure.linearStructure.operation.Queue;
import structure.treeStructure.collection.operation.Map;
import structure.treeStructure.collection.operation.Visitor;
import util.printer.BinaryTreeInfo;
import util.printer.BinaryTrees;

import java.util.Objects;

@SuppressWarnings("all")
public class HashMap<K, V> implements Map<K, V> {
    private int size;

    private static final boolean RED = false;

    private static final boolean BLACK = true;

    private static final int DEFAULT_CAPACITY = 16;

    private static final float DEFAULT_LOAD_FACTOR = 0.75f;

    private Node<K, V>[] table;

    private static class Node<K, V> {
        private K key;
        private V value;

        private int hashCode;
        private boolean color = RED;
        private Node<K, V> left;
        private Node<K, V> right;
        private Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.parent = parent;
            this.key = key;
            this.value = value;
            this.hashCode = key == null ? 0 : key.hashCode();
        }

        /**
         * @return 判断是否是叶子结点
         */
        public boolean isLeaf() {
            return left == null && right == null;
        }

        /**
         * @return 判断是否有左子结点
         */
        public boolean withLeftNode() {
            return left != null;
        }

        /**
         * @return 判断是否有右子结点
         */
        public boolean withRightNode() {
            return right != null;
        }

        public boolean isLeftOfTheFather() {
            return parent != null && parent.left == this;
        }

        public boolean isRightOfTheFather() {
            return parent != null && parent.right == this;
        }

        public Node<K, V> sibling() {
            if (isLeftOfTheFather()) {
                return parent.right;
            }

            if (isRightOfTheFather()) {
                return parent.left;
            }

            return null;
        }
    }

    public HashMap() {
        table = new Node[DEFAULT_CAPACITY];
    }

    public void printTree(){
        int[] i = new int[1];
        for (i[0] = 0; i[0] < table.length; i[0]++){
            if (table[i[0]] == null) return;
            print(new BinaryTreeInfo() {
                @Override
                public Object root() {
                    return table[i[0]];
                }

                @Override
                public Object left(Object node) {
                    return ((Node<K, V>) node).left;
                }

                @Override
                public Object right(Object node) {
                    return ((Node<K, V>) node).right;
                }

                @Override
                public Object string(Object node) {
                    Node<K, V> kvNode = (Node<K, V>) node;
                    String kvString;
                    if (kvNode != null) kvString = "key:" + kvNode.key + " value:" + kvNode.value;
                    else kvString = "key:null value:null";
                    return kvString;
                }

            });
            System.out.println("index【" + i[0] + "】^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
        }

    }
    private void print (BinaryTreeInfo root){
        BinaryTrees.println(root);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        if (size == 0) return;
        for (int i = 0; i < table.length; i++) {
            table[i] = null;
        }
        size = 0;
    }

    @Override
    public V put(K key, V value) {
        int index = indexForKey(key);
        // 添加第一个节点
        if (table[index] == null) {
            table[index] = new Node<>(key, value, null);
            size++;
            // 新添加节点之后的处理
            addAfter(table[index]);
            return null;
        }
        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent = table[index];
        Node<K, V> node = table[index];
        Node<K, V> result = null;
        boolean searched = false;
        int cmp = 0;
        int hash1 = key == null ? 0 : key.hashCode();
        do {
            parent = node;
            if (hash1 > node.hashCode){
                cmp = 1;
            }else if (hash1 < node.hashCode){
                cmp = -1;
            } else if (Objects.equals(key, node.key)) {
                cmp = 0;
            } else if (key != null && node.key != null
                        && (cmp =  key.getClass().getName().compareTo(node.key.getClass().getName())) != 0
                        && key instanceof Comparable
                        && (cmp = ((Comparable) key).compareTo(node.key)) != 0
            ) {

            } else if (searched) {
                cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
            } else{
                //进行子树扫描
                if ((node.left != null && (result = node(node.left, key)) != null)
                        || (node.right != null && (result = node(node.right, key)) != null)) {
                    node = result;
                    cmp = 0;
                }else {
                    //没有找到
                    searched = true;
                    cmp = System.identityHashCode(key) - System.identityHashCode(node.key);
                }
            }
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.key = key;
                V oldValue = node.value;
                node.value = value;
                return oldValue;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        Node<K, V> newNode = new Node<>(key, value, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        // 新添加节点之后的处理
        addAfter(newNode);
        return null;
    }

    @Override
    public V get(K key) {
        return node(key) == null ? null : node(key).value;
    }

    @Override
    public V remove(K key) {
        return remove(node(key));
    }

    private V remove(Node<K, V> node){
        if (node == null) return null;
        size--;
        V value = node.value;
        if (node.withLeftNode() && node.withRightNode()) { // 度为2的节点
            // 找到后继节点
            Node<K, V> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.key = s.key;
            node.value = s.value;
            node.hashCode = s.hashCode;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<K, V> replacement = node.left != null ? node.left : node.right;

        if (replacement != null) { // node是度为1的节点
            // 更改parent
            replacement.parent = node.parent;
            // 更改parent的left、right的指向
            if (node.parent == null) { // node是度为1的节点并且是根节点
                table[indexForHash(node.hashCode)] = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            removeAfter(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            table[indexForHash(node.hashCode)] = null;

            // 删除节点之后的处理
            removeAfter(node);
        } else { // node是叶子节点，但不是根节点
            if (node == node.parent.left) {
                node.parent.left = null;
            } else { // node == node.parent.right
                node.parent.right = null;
            }

            // 删除节点之后的处理
            removeAfter(node);
        }
        return value;
    }

    @Override
    public boolean containsKey(K key) {
        return node(key) != null;
    }

    @Override
    public boolean containsValue(V value) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Node<K, V> node = table[i];
            Stack<Node<K, V>> stack = new ArrayStack<>();
            stack.push(table[i]);
            while (!stack.isEmpty()) {
                if ((node = (node == null ? null : node.left)) != null) {
                    stack.push(node);
                } else {
                    node = stack.pop();
                    if (Objects.equals(node.value, value))
                        return true;
                    node = node.right;
                    if (node != null) {
                        stack.push(node);
                    }
                }
            }
        }
        return false;
    }

    @Override
    public void traversal(Visitor<K, V> visitor) {
        for (int i = 0; i < table.length; i++) {
            if (table[i] == null) continue;
            Node<K, V> node = table[i];
            Stack<Node<K, V>> stack = new ArrayStack<>();
            stack.push(table[i]);
            while (!stack.isEmpty()) {
                if ((node = (node == null ? null : node.left)) != null) {
                    stack.push(node);
                } else {
                    node = stack.pop();
                    if (visitor.visit(node.key, node.value)) return;
                    node = node.right;
                    if (node != null) {
                        stack.push(node);
                    }
                }
            }
        }
    }

    protected void addAfter(Node<K, V> node) {
        Node<K, V> parent = node.parent;
        // 添加的是根节点 或者 上溢到达了根节点
        if (parent == null) {
            black(node);
            return;
        }
        // 如果父节点是黑色，直接返回
        if (isBlack(parent)) return;

        // 叔父节点
        Node<K, V> uncle = parent.sibling();
        // 祖父节点
        Node<K, V> grand = red(parent.parent);
        if (isRed(uncle)) { // 叔父节点是红色【B树节点上溢】
            black(parent);
            black(uncle);
            // 把祖父节点当做是新添加的节点
            addAfter(grand);
            return;
        }

        // 叔父节点不是红色
        if (parent.isLeftOfTheFather()) { // L
            if (node.isLeftOfTheFather()) { // LL
                black(parent);
            } else { // LR
                black(node);
                leftRotation(parent);
            }
            rightRotation(grand);
        } else { // R
            if (node.isLeftOfTheFather()) { // RL
                black(node);
                rightRotation(parent);
            } else { // RR
                black(parent);
            }
            leftRotation(grand);
        }
    }

    protected void removeAfter(Node<K, V> node) {
        // 如果删除的节点是红色
        // 或者 用以取代删除节点的子节点是红色
        if (isRed(node)) {
            black(node);
            return;
        }
        Node<K, V> parent = node.parent;
        // 删除的是根节点
        if (parent == null) return;

        // 删除的是黑色叶子节点【下溢】
        // 判断被删除的node是左还是右
        boolean left = parent.left == null || node.isLeftOfTheFather();
        Node<K, V> sibling = left ? parent.right : parent.left;
        if (left) { // 被删除的节点在左边，兄弟节点在右边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                leftRotation(parent);
                // 更换兄弟
                sibling = parent.right;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    removeAfter(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.right)) {
                    rightRotation(sibling);
                    sibling = parent.right;
                }

                color(sibling, colorOf(parent));
                black(sibling.right);
                black(parent);
                leftRotation(parent);
            }
        } else { // 被删除的节点在右边，兄弟节点在左边
            if (isRed(sibling)) { // 兄弟节点是红色
                black(sibling);
                red(parent);
                rightRotation(parent);
                // 更换兄弟
                sibling = parent.left;
            }

            // 兄弟节点必然是黑色
            if (isBlack(sibling.left) && isBlack(sibling.right)) {
                // 兄弟节点没有1个红色子节点，父节点要向下跟兄弟节点合并
                boolean parentBlack = isBlack(parent);
                black(parent);
                red(sibling);
                if (parentBlack) {
                    removeAfter(parent);
                }
            } else { // 兄弟节点至少有1个红色子节点，向兄弟节点借元素
                // 兄弟节点的左边是黑色，兄弟要先旋转
                if (isBlack(sibling.left)) {
                    leftRotation(sibling);
                    sibling = parent.left;
                }

                color(sibling, colorOf(parent));
                black(sibling.left);
                black(parent);
                rightRotation(parent);
            }
        }
    }

    private Node<K, V> color(Node<K, V> node, boolean color) {
        if (node == null) return null;
        node.color = color;
        return node;
    }

    private Node<K, V> red(Node<K, V> node) {
        return color(node, RED);
    }

    private Node<K, V> black(Node<K, V> node) {
        return color(node, BLACK);
    }

    private boolean colorOf(Node<K, V> node) {
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack(Node<K, V> node) {
        return colorOf(node) == BLACK;
    }

    private boolean isRed(Node<K, V> node) {
        return colorOf(node) == RED;
    }


    /**
     * @param element 根据key找到节点
     * @return 返回找到的节点
     */

    private  Node<K, V> node(K key) {
        int index = indexForKey(key);
        Node<K, V> root = table[index];
        if (root == null) return null;
        return node(root, key);
    }

    /**
     * 重写hashCode要求：对象equals相同则hashCode一定相同，hashCode相同对象的equals不一定相同
     * 先比较hash值的大小，hash大的元素放右边，hash值小的放左边，相等则进行对象equals比较
     * @param node 查找以该节点为根节点的子树上的所有节点
     * @param key 以key为线索
     * @return 返回与key equals的节点key的节点
     */

    protected Node<K, V> node(Node<K, V> node,K key) {
        Node<K, V> result = null;
        int compare = 0;
        int hash1 = key == null ? 0 : key.hashCode();
        do {
            if (hash1 > node.hashCode) {
                node = node.right;
            } else if (hash1 < node.hashCode) {
                node = node.left;
            } else if (Objects.equals(key, node.key)){
                //hashCode相等，比较对象的equals
                return node;
            } else if (key != null && node.key != null
                    && (compare = key.getClass().getName().compareTo(node.key.getClass().getName())) != 0
                    && key instanceof Comparable
                    && (compare = ((Comparable) key).compareTo(node.key)) != 0
                    ) {
                //是同一类的对象，不equals但Comparable
                node = compare > 0 ? node.right : node.left;
            }
            //扫描整棵子树
            else if (node.right != null && (result = node(node.right, key)) != null) {
                return result;
            } else { // 只能往左边找
                node = node.left;
            }
        }while (node != null);
        return null;
    }

    /**
     * @param node 传入一个节点，找到它的后继节点
     * @return 返回后继节点
     */
    protected Node<K, V> successor(Node<K, V> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<K, V> p = node.right;
        if (p != null) {
            while (p.left != null) {
                p = p.left;
            }
            return p;
        }

        // 从父节点、祖父节点中寻找前驱节点
        while (node.parent != null && node == node.parent.right) {
            node = node.parent;
        }

        return node.parent;
    }

    /**
     * @param grand 右旋转
     */
    protected void rightRotation(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        grand.left = parent.right;
        if (parent.right != null) {
            parent.right.parent = grand;
        }
        if (grand.parent == null) {
            table[indexForHash(grand.hashCode)] = parent;
        } else if (grand.isLeftOfTheFather()) {
            grand.parent.left = parent;
        } else {
            grand.parent.right = parent;
        }
        parent.right = grand;
        parent.parent = grand.parent;
        grand.parent = parent;
    }

    /**
     * @param grand 左旋转
     */
    protected void leftRotation(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        grand.right = parent.left;
        if (parent.left != null) {
            parent.left.parent = grand;
        }
        if (grand.parent == null) {
            table[indexForHash(grand.hashCode)] = parent;
        } else if (grand.isLeftOfTheFather()) {
            grand.parent.left = parent;
        } else {
            grand.parent.right = parent;
        }
        parent.left = grand;
        parent.parent = grand.parent;
        grand.parent = parent;
    }

    /**
     * 根据key生成索引
     *
     * @param key
     * @return 哈希表索引
     */
    private int indexForKey(K key) {
        if (key == null) return 0;
        int hashCode = key.hashCode();
        return (hashCode ^ (hashCode >>> 16)) & (table.length - 1);
    }

    private int indexForHash(int hash) {
        return (hash ^ (hash >>> 16)) & (table.length - 1);
    }

    private int indexForNode(Node<K, V> newNode) {
        return indexForHash(newNode.hashCode);
    }

    private void resize() {
        // 装填因子 <= 0.75
        if ((float)(size / table.length) <= DEFAULT_LOAD_FACTOR) return;

        Node<K, V>[] oldTable = table;
        table = new Node[oldTable.length << 1];

        Queue<Node<K, V>> queue = new LinkedQueue<>();
        for (int i = 0; i < oldTable.length; i++) {
            if (oldTable[i] == null) continue;
            queue.enQueue(oldTable[i]);
            while (!queue.isEmpty()) {
                Node<K, V> node = queue.deQueue();
                if (node.left != null) {
                    queue.enQueue(node.left);
                }
                if (node.right != null) {
                    queue.enQueue(node.right);
                }
                // 挪动代码得放到最后面
                moveNode(node);
            }
        }
    }

    private void moveNode(Node<K, V> newNode) {
        // 重置
        newNode.parent = null;
        newNode.left = null;
        newNode.right = null;
        newNode.color = RED;

        int index = indexForNode(newNode);
        // 取出index位置的红黑树根节点
        Node<K, V> root = table[index];
        if (root == null) {
            root = newNode;
            table[index] = root;
            addAfter(root);
            return;
        }

        // 添加新的节点到红黑树上面
        Node<K, V> parent = root;
        Node<K, V> node = root;
        int cmp = 0;
        K k1 = newNode.key;
        int h1 = newNode.hashCode;
        do {
            parent = node;
            K k2 = node.key;
            int h2 = node.hashCode;
            if (h1 > h2) {
                cmp = 1;
            } else if (h1 < h2) {
                cmp = -1;
            } else if (k1 != null && k2 != null
                    && k1 instanceof Comparable
                    && k1.getClass() == k2.getClass()
                    && (cmp = ((Comparable)k1).compareTo(k2)) != 0) {
            } else {
                cmp = System.identityHashCode(k1) - System.identityHashCode(k2);
            }

            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        newNode.parent = parent;
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }

        // 新添加节点之后的处理
        addAfter(newNode);
    }

}
