package structure.treeStructure.binaryTree.operation;

import structure.linearStructure.linked.queue.LinkedQueue;
import structure.linearStructure.operation.Queue;
import structure.treeStructure.operation.Comparator;
import structure.treeStructure.operation.Visitor;

@SuppressWarnings("all")
public abstract class AbstractBinaryTree<E> implements BinaryTree<E>{

    protected int size;

    protected Node<E> root;

    /**
     * 比较器，如果有比较器就优先使用比较器比较元素大小，
     * 如果没有比较器，就要求元素必须是可比较的，是实现
     * 了 Comparable 接口的元素
     */
    private Comparator<E> comparator;

    public AbstractBinaryTree() {
    }
    public AbstractBinaryTree(Comparator<E> comparator) {
        this.comparator = comparator;
    }

    protected static class Node<E> {

        public E element;
        public Node<E> left;
        public Node<E> right;
        public Node<E> parent;

        public Node(E element, Node<E> parent) {
            this.parent = parent;
            this.element = element;
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

        public Node<E> sibling() {
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
    public void add(E element) {
        elementNotNullCheck(element);
        // 添加第一个节点
        if (root == null) {
            root = createNode(element, null);
            size++;
            // 新添加节点之后的处理
            addAfter(root);
            return;
        }

        // 添加的不是第一个节点
        // 找到父节点
        Node<E> parent = root;
        Node<E> node = root;
        int cmp = 0;
        do {
            cmp = compare(element, node.element);
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else { // 相等
                node.element = element;
                return;
            }
        } while (node != null);

        // 看看插入到父节点的哪个位置
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;

        // 新添加节点之后的处理
        addAfter(newNode);
    }

    @Override
    public void remove(E element) {
        remove(node(element));
    }
    private void remove(Node<E> node) {
        if (node == null) return;

        size--;

        if (node.withLeftNode()&&node.withRightNode()) { // 度为2的节点
            // 找到后继节点
            Node<E> s = successor(node);
            // 用后继节点的值覆盖度为2的节点的值
            node.element = s.element;
            // 删除后继节点
            node = s;
        }

        // 删除node节点（node的度必然是1或者0）
        Node<E> replacement = node.left != null ? node.left : node.right;

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
    }

    @Override
    public void clear() {
        size = 0;
        root = null;
    }

    @Override
    public boolean contains(E element) {
        return node(element) != null;
    }

    /**
     * 添加之后对树的调整，交给子类去实现
     * @param node 新添加的节点
     */
    protected abstract void addAfter(Node<E> node);

    protected abstract void removeAfter(Node<E> node);

    protected abstract Node<E> createNode(E element,Node<E> parent);
    public void inorderTraversal(Visitor<E> visitor) {
        if (visitor == null) return;
        inorderTraversal(root, visitor);
    }

    /**
     * 中序遍历二叉树
     * @param node    从哪个节点开始遍历
     * @param visitor 观察者，交由调用者实现
     */
    protected void inorderTraversal(Node<E> node, Visitor<E> visitor) {
        if (node == null || Visitor.stop) return;
        inorderTraversal(node.left, visitor);
        //上一次设置如果停止了条件就结束遍历
        if (Visitor.stop) return;
        visitor.visit(node.element);
        inorderTraversal(node.right, visitor);
    }

    /**
     * 使用队列层序遍历整棵树
     *
     * @param visitor 观察者，交由调用者实现
     */
    public void level_order_traversal(Visitor<E> visitor) {
        if (root == null) return;

        Queue<Node<E>> queue = new LinkedQueue<>();
        queue.enQueue(root);
        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();
            if (Visitor.stop) {
                return;
            }
            visitor.visit(node.element);
            Node<E> rightNode = node.right;
            Node<E> leftNode = node.left;
            if (leftNode != null) {
                queue.enQueue(leftNode);
            }
            if (rightNode != null) {
                queue.enQueue(node.right);
            }
        }
    }

    /**
     * @param type flase:递归方式获取树的高度 true:队列层序遍历方式获取树的高度
     *             默认是true
     * @return 树高
     */
    public int treeHeight() {
        return treeHeight(true);
    }

    public int treeHeight(boolean type) {
        if (!type) return nodeHeight(root);
        return level_order_traversal_tree_Height(root);
    }

    /**
     * @param node 通过递归获取树中某个节点的高度
     * @return 节点高度
     */
    public int nodeHeight(Node<E> node) {
        if (node == null) return 0;
        return 1 + Math.max(nodeHeight(node.left), nodeHeight(node.right));
    }

    /**
     * @param node 通过队列进行层序遍历获取树中某个节点的高度
     * @return 节点高度
     */
    protected int level_order_traversal_tree_Height(Node<E> eNode) {
        if (eNode == null) return 0;
        Queue<Node<E>> queue = new LinkedQueue<>();
        int oneLayerNumber = 1;
        int height = 0;
        queue.enQueue(eNode);
        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();
            oneLayerNumber--;
            Node<E> rightNode = node.right;
            Node<E> leftNode = node.left;

            if (leftNode != null) {
                queue.enQueue(leftNode);
            }
            if (rightNode != null) {
                queue.enQueue(node.right);
            }
            if (oneLayerNumber == 0) {
                oneLayerNumber = queue.size();
                height++;
            }
        }
        return height;
    }

    /**
     * @return 判断一颗二叉树是否为完全二叉树
     */
    public boolean isCompleteBinaryTree() {
        if (root == null) throw new IllegalArgumentException("root must not be null!");
        Queue<Node<E>> queue = new LinkedQueue<>();
        queue.enQueue(root);

        boolean isLeaf = false;
        while (!queue.isEmpty()) {
            Node<E> node = queue.deQueue();
            if (isLeaf) {
                if (!node.isLeaf()) {
                    return false;
                }
            }
            if (node.withLeftNode() && !node.withRightNode()) {
                //设置标志，true表示接下来遍历到的元素必须是叶子结点，否则返回false
                isLeaf = true;
            } else if (!node.withLeftNode() && !node.withRightNode()) {
                isLeaf = true;
            } else if (!node.withLeftNode() && node.withRightNode()) {
                //没有左子结点，但又有右子节点，不符合完全二叉树的定义，返回false
                return false;
            }
            Node<E> rightNode = node.right;
            Node<E> leftNode = node.left;
            if (leftNode != null) {
                queue.enQueue(leftNode);
            }
            if (rightNode != null) {
                queue.enQueue(node.right);
            }
        }
        return true;
    }

    /**
     * 翻转一颗二叉树
     */
    public void flipBinaryTree() {
        if (root == null) return;
        Node<E> node = root;
        Queue<Node<E>> queue = new LinkedQueue<>();
        queue.enQueue(node);
        while (!queue.isEmpty()) {
            Node<E> eNode = queue.deQueue();
            //如果出队节点不为叶子节点就交换该节点的左右子树
            if (!eNode.isLeaf()) {
                Node<E> temp = eNode.right;
                eNode.right = eNode.left;
                eNode.left = temp;
            }
            if (eNode.withLeftNode()) {
                queue.enQueue(eNode.left);
            }
            if (eNode.withRightNode()) {
                queue.enQueue(eNode.right);
            }
        }
    }

    /**
     * @param element 根据元素找到节点
     * @return 返回找到的节点
     */
    protected Node<E> node(E element) {
        if (root == null) throw new IllegalArgumentException("root must not be null");
        Node<E> node = root;
        int compare;
        while(node != null){
            compare = compare(element, node.element);
            if (compare > 0){
                node = node.right;
            } else if (compare < 0) {
                node = node.left;
            }else {
                return node;
            }
        }
        return node;
    }

    /**
     * @param node 传入一个节点，找到它的前驱节点
     * @return 返回前驱节点
     */
    protected Node<E> precursor(Node<E> node) {
        if (node.withLeftNode()) {
            Node<E> leftNode = node.left;
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

    protected Node<E> successor(Node<E> node) {
        if (node == null) return null;

        // 前驱节点在左子树当中（right.left.left.left....）
        Node<E> p = node.right;
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
     * 必须实现Comparable接口。
     * @param e1 比较参数e1
     * @param e2 比较参数e2
     * @return 返回值等于0，代表e1等于e2，返回值大于0，e1大于e2，返回值小于0，e1小于e2
     *
     */
    protected int compare(E e1, E e2) {
        return comparator == null ? ((Comparable<E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }
    private void elementNotNullCheck(E element) {
        if (element == null) {
            throw new IllegalArgumentException("element must not be null");
        }
    }
}
