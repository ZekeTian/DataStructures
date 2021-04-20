package rbtree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import map.Map;

/**
 * 红黑树实现
 * 因为 2-3 树是绝对平衡的，而红黑树与 2-3 树是等价，所以红黑树也具有一定的平衡性，是一种近似平衡。
 * 但是，需要注意的是：红黑树与 2-3 树是等价的，但不等于。
 * 以 AVL 树中的平衡定义来衡量 AVL 树、2-3 树、红黑树，则三者平衡性的大小关系如下：
 *      2-3 树（绝对平衡） > AVL 树（平衡） > 红黑树（近似平衡）
 *      
 * 红黑树的性质：
 *  （1）每个节点是红色或者是黑色
 *  （2）根节点是黑色的
 *  （3）每一个叶子节点（最后的空节点）是黑色的
 *  （4）如果一个节点是红色的，那么他的孩子节点都是黑色的
 *      对于一个孩子节点而言，其要么是 2-3 树中的  2 节点，要么是 2-3 树中的 3 节点。
 *      如果是 2 节点，则其就是黑色；如果是 3 节点，对其进行拆分，红节点是左节点，黑节点是根节点（因为根节点与父节点相连，所以相对于父节点而言，孩子节点还是黑色）
 *      注意：对于左倾红黑树而言，如果一个节点是黑色的，那么他的右子节点是黑色的（原因同上）
 *  （5）从任意一个节点到叶子节点，经过的黑色节点的个数是一样的（经过红色节点的个数不一定一样）
 *      在 2-3 树中，由于其绝对平衡的特点，任意一个节点到叶子节点的高度是一样的。
 *      而由于 2-3 树与红黑树等价，任意一个节点到叶子节点的高度等于该节点到叶子节点所经过的黑节点个数，即有：从任意一个节点到叶子节点，经过的黑色节点是一样的。
 *      
 *   注意：
 *   （1）红黑树是一种近似平衡的树。
 *      正因为于此，所以若只是查找，则其性能比 AVL 树差；但是若考虑添加、删除、更新、查找四种操作，其综合性能要比 AVL 树好。
 *      因为 AVL 树在添加、删除时的自平衡操作要比红黑树的操作更繁琐、更耗时。
 *   （2）若 2-3 树的高度是 O(logn)，则最坏情况下，红黑树的高度是 2 * O(logn)。
 *      在最坏情况下，2-3 树的一条路径上的所有节点全部是 3 节点。当转换成红黑树，需要将这些 3 节点全部转换成 2 节点，高度变成原来的两倍。
 *      
 *   二分搜索树、AVL 树、红黑树的性能总结：
 *   （1）二分搜索树：对于完全随机的数据，其性能不差。但是在极端情况下，会退化成链表（即插入的元素完全有序）或高度不平衡（即插入的元素基本有序）
 *   （2）AVL 树：查找性能较好，但是自平衡操作较复杂，耗时略多
 *   （3）红黑树：是一种近似平衡的树，牺牲了一定的平衡性，高度从 logn 变成 2*logn，查找性能比 AVL 树差，但是统计性能更优（综合增删改查操作）
 */
public class RBTree<K extends Comparable<K>, V> implements Map<K, V> {

    /**
     * 节点的颜色标记
     */
    private static final boolean RED = true;
    private static final boolean BLACK = false;

    private class Node {
        K key;
        V val;
        Node left;
        Node right;
        boolean color;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            left = null;
            right = null;
            color = RED; // 由于 2-3 树添加操作的限制，新添加的节点不能添加到空节点的位置上，因此新添加的节点都是和已有节点融合。
                         // 而在红黑树中，与其他节点融合的节点是红色的，因此新节点默认颜色为红色。
        }
    }

    private Node root;
    private int size;

    public RBTree() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(K key) {
        return null != get(root, key);
    }

    @Override
    public void put(K key, V value) {
        root = put(root, key, value);
        root.color = BLACK; // 保持根节点始终是黑色
    }

    @Override
    public V remove(K key) {
        Node node = get(root, key);
        if (null == node) {
            return null;
        }
        
        // 树中存在 key 对应的节点，则执行删除操作
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
     * 验证是否是二分搜索树。如果是，则返回 true；否则，返回 false。
     * @return 如果树是二分搜索树，则返回 true；否则，返回 false。
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
    
    
    private class NodeInfo {
        NodeInfo parent;
        Node left;
        Node right;
        boolean color;
        K key;
        
        public NodeInfo(NodeInfo parent, Node cur) {
            this.parent = parent;
            this.left = cur.left;
            
            this.right = cur.right;
            this.color = cur.color;
            this.key = cur.key;
        }
        
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            
            if (null == parent) {
                builder.append("根顶点: ")
                       .append(key + " - " + getColorInfo(color));
            } else {
                builder.append("parent: " + parent.key + ", ")
                       .append("node: " + key + " - " + getColorInfo(color));
            }
            
            
            return builder.toString();
        }
    }
    
    /**
     * 按照层序，打印红黑树
     */
    public void print() {
        LinkedList<NodeInfo> queue =  new LinkedList<NodeInfo>();
        
        queue.add(new NodeInfo(null, root));
        
        while (!queue.isEmpty()) {
            NodeInfo node = queue.poll();
            
            System.out.println(node);
            
            if (null != node.left) {
                queue.add(new NodeInfo(node, node.left));
            }
            if (null != node.right) {
                queue.add(new NodeInfo(node, node.right));
            }
        }
    }
    
    
    
    /**
     * 在以  root 为根顶点的树中，获取键值为 key 的节点。
     * @param root  根顶点
     * @param key   键值
     * @return  键值为 key 的节点。如果能找到，则返回该节点；否则，返回 null
     */
    private Node get(Node root, K key) {
        if (null == root) {
            return null;
        }
        
        if (key.compareTo(root.key) < 0) {
            return get(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            return get(root.right, key);
        } else { // key == root.key
            return root;
        }
    }
    
    /**
     * 在以 root 为根顶点的树中，如果不存在 key 对应的节点，则添加节点 (key, val)，否则将 key 对应节点的值更新为 val。
     * @param root  根顶点
     * @param key   键
     * @param val   值
     * @return      添加顶点后，新树的根顶点
     */
    private Node put(Node root, K key, V val) {
        if (null == root) {
            ++size;
            return new Node(key, val);
        }
        
        if (key.compareTo(root.key) < 0) {
            root.left = put(root.left, key, val);
        } else if (key.compareTo(root.key) > 0) {
            root.right = put(root.right, key, val);
        } else { // key == root.key
            root.val = val;
        }
        
        // 添加节点后，根据树的形状及节点颜色，进行相应操作
        if (isRed(root.right) && !isRed(root.left)) {
            root = leftRotate(root);
        }
        
        if (isRed(root.left) && isRed(root.left.left)) {
            root = rightRotate(root);
        }
        
        if (isRed(root.left) && isRed(root.right)) {
            flipColor(root);
        }
        
        return root;
    }
    
    /**
     * 在以 root 为根顶点的树中，取出最小节点
     * @param root 根顶点
     * @return 树中最小的节点
     */
    private Node getMinNode(Node root) {
        if (null == root || null == root.left) {
            return root;
        }
        
        return getMinNode(root.left);
    }
    
    /**
     * 在以 root 为根顶点的树中，删除最小节点，并返回新树的根顶点。
     * @param root 根顶点
     * @return 删除最小顶点后，新树的根顶点
     */
    private Node removeMinNode(Node root) {
        if (null == root) {
            return null;
        }
        
        if (null == root.left) { // 最小节点
            --size;
            Node ret = root.right;
            root.right = null;
            return ret;
        }
        
        root.left = removeMinNode(root.left);
        
        return root;
    }
    
    
    /**
     * 在以 root 为根顶点的树中，删除 key 对应的节点。（调用此函数，必须确保树中存在 key）
     * @param root 根顶点
     * @param key  待删除顶点的键值
     * @return     删除顶点后，新树的根顶点
     */
    private Node remove(Node root, K key) {
        if (null == root) {
            return null;
        }
        
        if (key.compareTo(root.key) < 0) {
            root.left = remove(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            root.right = remove(root.right, key);
        } else { // key == root.key
            if (null == root.left) {
                Node ret = root.right;
                root.right = null;
                --size;
                return ret;
            }
            
            if (null == root.right) {
                Node ret = root.left;
                root.left = null;
                --size;
                return ret;
            }
            
            // 左右子树均不为空，则从右子树中取出最小顶点来代替 root
            Node minNode = getMinNode(root.right);
            minNode.right = removeMinNode(root.right);
            minNode.left = root.left;
            root.left = root.right = null;
            return minNode;
        }
        
        return root;
    }
    
    private void inOrder(Node root, List<K> result) {
        if (null == root) {
            return;
        }
        
        inOrder(root.left, result);
        result.add(root.key);
        inOrder(root.right, result);
    }
    
    private String getColorInfo(boolean color) {
        return RED == color ? "red" : "black"; 
    }
    
    /**
     * 判断节点 node 是否为红色。
     * @param node
     * @return 如果节点是红色，返回 true；否则，返回 false。
     */
    private boolean isRed(Node node) {
        if (null == node) {
            return false; // 空节点为黑色
        }
        
        return node.color == RED;
    }
    
    /**
     *          node                         x
     *          /  \        左旋转           /  \ 
     *         T1   x    ========>       node  T3 
     *             / \                   /  \  
     *            T2 T3                 T1  T2
     * 
     * 将以 node 为根顶点的树进行左旋转，并返回旋转后树的根顶点
     * @param node 根顶点
     * @return 旋转后树的根顶点
     */
    private Node leftRotate(Node node) {
        // 左旋转
        Node x = node.right;
        node.right = x.left;
        x.left = node;
        
        // 变换根顶点颜色
        x.color = node.color;
        node.color = RED;
        
        return x;
    }
    
    /**
     *          node                         x
     *          /  \        右旋转            / \
     *         x   T2     ========>        y  node 
     *        / \                             /  \
     *       y  T1                           T1  T2
     * 
     * 将以 node 为根顶点的树进行右旋转，并返回旋转后树的根顶点
     * @param node 根顶点
     * @return 旋转后树的根顶点
     */
    private Node rightRotate(Node node) {
        // 右旋转
        Node x = node.left;
        node.left = x.right;
        x.right = node;
        
        // 变换根顶点颜色
        x.color = node.color;
        node.color = RED;
        
        return x;
    }
    
    /**
     *              node(黑色)                       node(红色)
     *              /   \            颜色翻转         /   \
     *           L(红色)  R(红色)    ==========>   L(黑色) R(黑色)
     *             
     * 将以 node 为根顶点的树进行颜色翻转
     * @param node 根顶点
     * @return 颜色翻转后树的根顶点
     */
    private void flipColor(Node node) {
        node.color = RED;
        node.left.color = BLACK;
        node.right.color = BLACK;
    }
}
