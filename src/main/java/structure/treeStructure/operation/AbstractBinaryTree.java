package structure.treeStructure.operation;

import structure.linearStructure.linked.queue.LinkedQueue;
import structure.linearStructure.operation.Queue;

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
            return parent.left == this;
        }

        public boolean isRightOfTheFather() {
            return parent.right == this;
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
        //添加根节点
        if (root == null) {
            Node<E> node = createNode(element, null);
            root = node;
            size++;
            //进行添加节点后的调整
            addAfter(node);
            return;
        }
        //添加的不是根节点
        int cmp = 0;
        Node<E> node = root;
        Node<E> parent = null;
        while (node != null) {
            cmp = compare(element, node.element);
            //找到新添加元素的父节点
            parent = node;
            if (cmp > 0) {
                node = node.right;
            } else if (cmp < 0) {
                node = node.left;
            } else {
                node.element = element;
                return;
            }
        }
        //判断新元素加在父节点的哪个位置
        Node<E> newNode = createNode(element, parent);
        if (cmp > 0) {
            parent.right = newNode;
        } else {
            parent.left = newNode;
        }
        size++;
        //进行添加节点后的调整
        addAfter(newNode);
    }

    @Override
    public void remove(E element) {
        //查询该元素在二叉树中的节点
        Node<E> node = node(element);
        if (node == null) throw new IllegalArgumentException("The element does not exist in the container");
        size--;
        //删除叶子结点
        if (node.isLeaf()) {
            //1.是根节点
            if (node.parent == null) {
                root = null;
                //删除节点后的调整
                removeAfter(node);
                return;
            }
            //2.不是根节点，直接删除
            if (node.isLeftOfTheFather()) {
                node.parent.left = null;
            }else {
                node.parent.right = null;
            }
            //删除节点后的调整
            removeAfter(node);
            return;
        }
        //删除度为1的节点
        if (node.withLeftNode() && !node.withRightNode()) {
            if (node.parent != null){
                if (node.isLeftOfTheFather()){
                    node.parent.left = node.left;
                }
                else{
                    node.parent.right = node.left;
                }
            }else {
                //删除的是度为1的根节点
                root = node.left;
            }
            node.left.parent = node.parent;
            //删除节点后的调整
            removeAfter(node);
            return;
        }
        if (!node.withLeftNode() && node.withRightNode()) {
            if (node.parent != null){
                if (node.isRightOfTheFather()){
                    node.parent.right = node.right;
                }
                else{
                    node.parent.left = node.right;
                }
            }else{
                //删除的是度为1的根节点
                root = node.right;
            }
            node.right.parent = node.parent;
            //删除节点后的调整
            removeAfter(node);
            return;
        }
        //删除度为2的节点

        //把删除度为2的节点转化为删除前驱节点
        Node<E> precursor = precursor(node);
        node.element = precursor.element;
        //前驱节点是叶子结点
        if (precursor.isLeaf()) {
            if (precursor.isLeftOfTheFather()) precursor.parent.left = null;
            else precursor.parent.right = null;
            //删除节点后的调整
            removeAfter(precursor);
            return;
        }
        //前驱节点是度为1的节点，且只可能 是父亲节点的左孩子并自己有一个左孩子
        precursor.parent.right = precursor.left;
        precursor.left.parent = precursor.parent;
        //删除节点后的调整
        removeAfter(precursor);

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

    /**
     * 如果传入了比较器，优先使用比较器的逻辑，如果没有传入比较器，就要求容器中的元素必须是可比较的，
     * 必须实现我写的Comparable接口。（和JDK的Comparable接口内容一样但必须是我写的 》_《  JDK的不行哦~）
     * @param e1 比较参数e1
     * @param e2 比较参数e2
     * @return 返回值等于0，代表e1等于e2，返回值大于0，e1大于e2，返回值小于0，e1小于e2
     */
    protected int compare(E e1, E e2) {
        return comparator == null ? ((Comparable<E>) e1).compareTo(e2) : comparator.compare(e1, e2);
    }
}
