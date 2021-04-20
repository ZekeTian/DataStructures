package avl;

import java.util.ArrayList;
import java.util.List;

import map.Map;

/**
 * AVL 树，含有自平衡机制的二分搜索树
 * 平衡二分搜索树的特点：对于任意一个 节点，其左、右子树的高度差不大于 1（即 <= 1，这就是“平衡”二字的含义）
 *
 * @param <K> key 的类型
 * @param <V> value 的类型
 */
public class AVLTree<K extends Comparable<K>, V> implements Map<K, V> {
    private class Node {
        K key;
        V val;
        Node left;
        Node right;
        int height;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
            this.height = 1;
        }
    }

    /**
     * 根顶点
     */
    private Node root;

    /**
     * AVL 树中的元素个数
     */
    private int size;

    public AVLTree() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(K key) {
        return get(root, key) != null;
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
    }

    @Override
    public V remove(K key) {
        if (null == root) {
            throw new IllegalArgumentException("AVL Tree is empty!");
        }

        Node node = get(root, key);
        if (null == node) {
            return null;
        }

        root = remove(root, key);
        return node.val;
    }

    @Override
    public V get(K key) {
        Node node = get(root, key);
        if (null == node) {
            return null;
        }
        return node.val;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 验证当前创建的树是否满足 BST 的特点
     * @return
     */
    public boolean isBST() {
        List<K> sortedList = new ArrayList<K>();
        inOrder(root, sortedList);

        for (int i = 1; i < sortedList.size(); ++i) {
            if (sortedList.get(i - 1).compareTo(sortedList.get(i)) > 0) {
                return false;
            }
        }

        return true;
    }

    public boolean isBalance() {
        return isBalance(root);
    }

    private boolean isBalance(Node root) {
        if (null == root) {
            return true;
        }

        int balanceFactor = getBalanceFactor(root);
        if (Math.abs(balanceFactor) > 1) {
            return false;
        }

        return isBalance(root.left) && isBalance(root.right);
    }

    /**
     * 在以 node 为根顶点的树中，若不存在 key 对应的顶点，则添加新顶点，即键值对 (key, val)。否则，用 val 更新 key 对应的顶点
     * 
     * @param root 树的根顶点
     * @param key  键
     * @param val  值
     * @return 添加节点后得到的新树的根顶点
     */
    private Node put(Node root, K key, V val) {
        if (null == root) {
            ++size;
            return new Node(key, val); // 递归到底，则创建一个新的顶点
        }

        // 判断添加子树的方向，将新节点添加到树中
        if (key.compareTo(root.key) < 0) { // 左子树
            root.left = put(root.left, key, val);
        } else if (key.compareTo(root.key) > 0) { // 右子树
            root.right = put(root.right, key, val);
        } else { // 新 key 与 root 的 key 相等
            root.val = val;
        }

        root.height = Math.max(getHeight(root.left), getHeight(root.right)) + 1;

        // 对树进行平衡处理
        if (getBalanceFactor(root) > 1 && getBalanceFactor(root.left) >= 0) { // LL 类型
            root = rightRotate(root);
        } else if (getBalanceFactor(root) < -1 && getBalanceFactor(root.right) <= 0) { // RR 类型
            root = leftRotate(root);
        } else if (getBalanceFactor(root) > 1 && getBalanceFactor(root.left) <= 0) { // LR 类型
            // 先将根顶点的左子树左旋转，转换成 LL 类型
            root.left = leftRotate(root.left);
            // 然后，将根顶点对应的树进行右旋转
            root = rightRotate(root);
        } else if (getBalanceFactor(root) < -1 && getBalanceFactor(root.right) >= 0) { // RL 类型
            // 先将根顶点的右子树右旋转，转换成 RR 类型
            root.right = rightRotate(root.right);
            // 然后，将根顶点对应的树进行左旋转
            root = leftRotate(root);
        }

        int balanceFactor = getBalanceFactor(root);
        if (Math.abs(balanceFactor) > 1) {
            System.out.println(root.key + " is unbalanced!");
        }

        return root;
    }

    /**
     *            y                       x
     *           / \                     /  \
     *          x  T4                   Z    y
     *         / \      y 右旋转         / \  / \ 
     *        Z  T3    =======>       T1 T2 T3 T4
     *       / \
     *      T1 T2  
     * 
     * 将以 y 为根顶点的树进行右旋转，并返回旋转后树的根顶点
     * @param y 根顶点
     * @return 旋转后树的根顶点
     */
    private Node rightRotate(Node y) {
        Node x = y.left;
        y.left = x.right;
        x.right = y;

        // 因为 x 的高度是根据左右子树 z、y 确定，因此需要先更新 y 的高度，然后才能更新 x 的高度
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    /**
     *            y                        x
     *           / \                      /  \
     *          T1  x                    y    z
     *             / \      y 左旋转      / \  / \
     *            T2 z      =======>   T1 T2 T3 T4
     *              / \
     *             T3 T4
     * 
     * 将以 y 为根顶点的树进行左旋转，并返回左旋转后树的根顶点
     * @param y 根顶点
     * @return 旋转后树的根顶点
     */
    private Node leftRotate(Node y) {
        Node x = y.right;
        y.right = x.left;
        x.left = y;

        // 因为 x 的高度需要根据左右子树 y、z 来确定，因此在更新 x 的高度之前要先更新 y 的高度，这样才能得到正确的 x 的高度
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    /**
     * 在以 root 为根顶点的树中，寻找 key 对应的顶点。
     * 
     * @param root 根顶点
     * @param key  键值
     * @return key 对应的顶点。若能找到，则返回该顶点；否则，返回 null 
     */
    private Node get(Node root, K key) {
        if (null == root) {
            return null;
        }

        if (key.compareTo(root.key) < 0) { // 左子树
            return get(root.left, key);
        } else if (key.compareTo(root.key) > 0) {// 右子树
            return get(root.right, key);
        } else { // key 对应的顶点即为 root
            return root;
        }
    }

    /**
     * 返回以 root 为根顶点的树中的最小值
     * @param root 根顶点
     * @return 最小值对应的节点
     */
    private Node getMinNode(Node root) {
        if (null == root) {
            return null;
        }

        if (null == root.left) {
            return root;
        }

        return getMinNode(root.left);
    }

    /**
     * 在以 root 为根顶点的树中，删除最小节点，并返回新树的根顶点
     * @param root 树的根顶点
     * @return 删除后，新树的根顶点
     */
    private Node removeMinNode(Node root) {
        if (null == root) {
            return null;
        }

        // 找到最小值
        if (null == root.left) {
            Node ret = root.right;
            root.right = null;
            --size;
            return ret;
        }

        root.left = removeMinNode(root.left);

        return root;
    }

    /**
     * 在以 root 为根顶点的树中，删除 key 对应的根顶点
     * @param root 树的根顶点
     * @param key 待删除顶点对应的 键值
     * @return 删除顶点后，新树的根顶点
     */
    private Node remove(Node root, K key) {
        Node ret = root; // 返回值，即新树的根顶点

        if (key.compareTo(root.key) < 0) { // 左子树
            root.left = remove(root.left, key);
        } else if (key.compareTo(root.key) > 0) { // 右子树
            root.right = remove(root.right, key);
        } else { // root 即为 key 对应的节点
            // 判断 root 左右子树是否存在，根据不同情况采取不同策略
            if (null == root.left) { // 左子树为空
                ret = root.right;
                root.right = null;
                --size;
            } else if (null == root.right) { // 右子树为空
                ret = root.left;
                root.left = null;
                --size;
            } else {
                // 左右子树均不为空，则从右子树中选择最小的顶点代替 root
                Node minNode = getMinNode(root.right);
                // minNode.right = removeMinNode(root.right); // 原来的方式，这种方式在 removeMinNode 时会发生不平衡。一种解决办法是，在 removeMinNode 中添加平衡处理；另一种方法是，复用 remove
                minNode.right = remove(root.right, minNode.key); // 复用 remove 函数删除最小的顶点，从而保证在删除最小顶点时依然平衡
                minNode.left = root.left;
                root.left = root.right = null;
                ret = minNode;
            }
        }

        if (null == ret) {
            return null;
        }

        // 更新 ret 节点的高度
        ret.height = Math.max(getHeight(ret.left), getHeight(ret.right)) + 1;

        // 进行平衡处理
        if (getBalanceFactor(ret) > 1 && getBalanceFactor(ret.left) >= 0) { // LL 类型
            ret = rightRotate(ret);
        } else if (getBalanceFactor(ret) < -1 && getBalanceFactor(ret.right) <= 0) { // RR 类型
            ret = leftRotate(ret);
        } else if (getBalanceFactor(ret) > 1 && getBalanceFactor(ret.left) <= 0) { // LR 类型
            // 先对 ret 的左子树进行左旋转，转换成 LL 类型
            ret.left = leftRotate(ret.left);
            // 然后，对 ret 进行右旋转
            ret = rightRotate(ret);
        } else if (getBalanceFactor(ret) < -1 && getBalanceFactor(ret.right) >= 0) { // RL 类型
            // 先对 ret 的右子树进行右旋转，转换成 RR 类型
            ret.right = rightRotate(ret.right);
            // 然后，再对 ret 进行左旋转
            ret = leftRotate(ret);
        }

        return ret;
    }

    /**
     * 中序遍历以 root 为根的树，并将结果保存在 resultList 中
     * @param root 根顶点
     * @param resultList 保存遍历时的访问顶点
     */
    private void inOrder(Node root, List<K> resultList) {
        if (null == root) {
            return;
        }

        inOrder(root.left, resultList);
        resultList.add(root.key);

        //        System.out.println(root.key + ": " + getHeight(root));

        inOrder(root.right, resultList);
    }

    /**
     * 获取节点 node 的高度
     * @param node 节点
     * @return 节点 node 的高度
     */
    private int getHeight(Node node) {
        if (null == node) {
            return 0;
        }

        return node.height;
    }

    /**
     * 获取节点 node 的平衡因子
     * @param node 节点
     * @return 节点 node 的平衡因子
     */
    private int getBalanceFactor(Node node) {
        if (null == node) {
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }
}
