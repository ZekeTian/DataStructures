package rbtree;

import java.util.ArrayList;
import java.util.List;

import map.Map;

/**
 * 红黑树实现
 * 因为 2-3 树是绝对平衡的，而红黑树与 2-3 树是等价，所以红黑树也具有一定的平衡性，是一种近似平衡。
 * 但是，需要注意的是：红黑树与 2-3 树是等价的，但不等于。
 * 以 AVL 树中的平衡定义来衡量 AVL 树、2-3 树、红黑树，则三者平衡性的大小关系如下：
 *      2-3 树（绝对平衡） > AVL 树（平衡） > 红黑树（近似平衡）
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
            color = RED;
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
}
