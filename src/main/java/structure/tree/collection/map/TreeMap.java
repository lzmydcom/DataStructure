package structure.tree.collection.map;

import structure.linear.array.stack.ArrayStack;
import structure.linear.array.stack.Stack;
import structure.tree.collection.operation.Map;
import structure.tree.collection.operation.Visitor;
import util.printer.BinaryTreeInfo;

import java.util.Comparator;
import java.util.Objects;


@SuppressWarnings("all")
public class TreeMap<K, V> implements Map<K, V>, BinaryTreeInfo {

    protected int size;

    protected Node<K, V> root;

    private static final boolean RED = false;

    private static final boolean BLACK = true;
    private Comparator<K> comparator;

    public TreeMap() {

    }

    public TreeMap(Comparator<K> comparator) {
        this.comparator = comparator;
    }

    @Override
    public Object root() {
        return root;
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
        String color = kvNode.color == true ? "B" : "R";
        String nodeStr = kvNode == null ? "null" : ("key:" + kvNode.key + " value:" + kvNode.value) + color;
        return nodeStr;
    }

    protected static class Node<K, V> {
        private K key;
        private V value;
        private boolean color = RED;
        private Node<K, V> left;
        private Node<K, V> right;
        private Node<K, V> parent;

        public Node(K key, V value, Node<K, V> parent) {
            this.parent = parent;
            this.key = key;
            this.value = value;
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
        size = 0;
        root = null;
    }

    @Override
    public V put(K key, V value) {
        // 添加第一个节点
        if (root == null) {
            root = new Node<>(key, value, null);
            size++;

            // 新添加节点之后的处理
            addAfter(root);
            return null;
        }

        // 添加的不是第一个节点
        // 找到父节点
        Node<K, V> parent = root;
        Node<K, V> node = root;
        V oldValue;
        int cmp = 0;
        do {
            cmp = compare(key, node.key);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.key = key;
                oldValue = node.value;
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
        Node<K, V> node = node(key);
        return node == null ? null : node.value;
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
                root = replacement;
            } else if (node == node.parent.left) {
                node.parent.left = replacement;
            } else { // node == node.parent.right
                node.parent.right = replacement;
            }

            // 删除节点之后的处理
            removeAfter(replacement);
        } else if (node.parent == null) { // node是叶子节点并且是根节点
            root = null;

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
    public V remove(K key) {
        return remove(node(key));
    }
    @Override
    public boolean containsKey (K key){
        return node(key) != null;
    }

    @Override
    public boolean containsValue (V value){
        final Object[] objects = new Object[2];
        objects[0] = value;
        objects[1] = false;
        traversal(new Visitor<K, V>() {
            @Override
            public boolean visit(K k, V v) {
                objects[1] = Objects.equals(objects[0], v);
                return (boolean) objects[1];
            }
        });
        return (boolean) objects[1];
    }

    /**
     * 非递归的中序遍历
     * @param visitor 观察者模式，对节点的访问交给调用者去实现
     */
    @Override
    public void traversal (Visitor < K, V > visitor){
        if (root == null) return;
        Node<K, V> node = root;
        Stack<Node<K, V>>  stack = new ArrayStack<>();
        stack.push(root);
        while (!stack.isEmpty()){
            if ((node = (node == null ? null : node.left)) != null){
                stack.push(node);
            }else {
                node = stack.pop();
                if (visitor.visit(node.key, node.value)) {
                    return;
                }
                node = node.right;
                if (node != null){
                    stack.push(node);
                }
            }
        }
    }
    
    protected void addAfter (Node <K, V> node) {
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
    
    protected void removeAfter (Node<K, V> node) {
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

    private Node<K, V> color (Node < K, V > node,boolean color){
        if (node == null) return null;
        node.color = color;
        return node;
    }

    private Node<K, V> red (Node < K, V > node){
        return color(node, RED);
    }

    private Node<K, V> black (Node < K, V > node){
        return color(node, BLACK);
    }

    private boolean colorOf (Node < K, V > node){
        return node == null ? BLACK : node.color;
    }

    private boolean isBlack (Node < K, V > node){
        return colorOf(node) == BLACK;
    }

    private boolean isRed (Node < K, V > node){
        return colorOf(node) == RED;
    }


    /**
     * @param element 根据key找到节点
     * @return 返回找到的节点
     */
    protected Node<K, V> node (K key){
        Node<K, V> node = root;
        int compare;
        while (node != null) {
            compare = compare(key, node.key);
            if (compare > 0) {
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            } else {
                return node;
            }
        }
        return node;
    }

    /**
     * @param node 传入一个节点，找到它的前驱节点
     * @return 返回前驱节点
     */
    protected Node<K, V> precursor (Node < K, V > node){
        if (node.withLeftNode()) {
            Node<K, V> leftNode = node.left;
            while (leftNode.withRightNode()) {
                leftNode = leftNode.right;
            }
            return leftNode;
        }
        //能来到这，说明该节点没有左子树
        if (node == root) {
            //是根节点
            return null;
        }
        //能来到这，说明是在节点的右子树
        while (node.isLeftOfTheFather()) {
            node = node.parent;
        }
        return node.parent;
    }

    protected Node<K, V> successor (Node < K, V > node){
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
     * 如果传入了比较器，优先使用比较器的逻辑，如果没有传入比较器，就要求容器中的元素必须是可比较的，
     * 必须实现我写的Comparable接口。（和JDK的Comparable接口内容一样但必须是我写的 》_《  JDK的不行哦~）
     * @param e1 比较参数e1
     * @param e2 比较参数e2
     * @return 返回值等于0，代表e1等于e2，返回值大于0，e1大于e2，返回值小于0，e1小于e2
     */
    protected int compare (K e1, K e2){
        return comparator == null ? ((Comparable<K>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }

    /**
     *
     * @param grand 右旋转
     */
    protected void rightRotation(Node<K, V> grand) {
        Node<K, V> parent = grand.left;
        grand.left = parent.right;
        if (parent.right != null) {
            parent.right.parent = grand;
        }
        if (grand.parent == null){
            root = parent;
        } else if (grand.isLeftOfTheFather()){
            grand.parent.left = parent;
        }else {
            grand.parent.right = parent;
        }
        parent.right = grand;
        parent.parent = grand.parent;
        grand.parent = parent;
    }

    /**
     *
     * @param grand 左旋转
     */
    protected void leftRotation(Node<K, V> grand) {
        Node<K, V> parent = grand.right;
        grand.right = parent.left;
        if (parent.left != null) {
            parent.left.parent = grand;
        }
        if (grand.parent == null){
            root = parent;
        } else if (grand.isLeftOfTheFather()){
            grand.parent.left = parent;
        }else {
            grand.parent.right = parent;
        }
        parent.left = grand;
        parent.parent = grand.parent;
        grand.parent = parent;
    }
}
