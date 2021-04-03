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
public class AVLTree<K extends Comparable<K>, V> implements Map<K, V>{
    private class Node {
        K key;
        V val;
        Node left;
        Node right;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
            this.left = null;
            this.right = null;
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
        
        if (!contains(key)) {
            return null;
        }
        
        return remove(root, key).val;
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
        
        // 判断添加子树的方向
        if (key.compareTo(root.key) < 0) { // 左子树
            root.left = put(root.left, key, val);
        } else if (key.compareTo(root.key) > 0) { // 右子树
            root.right = put(root.right, key, val);
        } else { // 新 key 与 root 的 key 相等
            root.val = val;
        }
        
        return root;
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
     * @return 被删除的顶点
     */
    private Node remove(Node root, K key) {
        if (key.compareTo(root.key) < 0) { // 左子树
            root.left = remove(root.left, key);
        } else if (key.compareTo(root.key) > 0) { // 右子树
            root.right = remove(root.right, key);
        } else { // root 即为 key 对应的节点
            // 判断 root 左右子树是否存在，根据不同情况采取不同策略
            if (null == root.left) { // 左子树为空
               Node ret = root.right;
               root.right = null;
               --size;
               return ret;
            }
            
            if (null == root.right) { // 右子树为空
                Node ret = root.left;
                root.left = null;
                --size;
                return ret;
            }
            
            // 左右子树均不为空，则从右子树中选择最小的顶点代替 root
            Node minNode = getMinNode(root.right);
            minNode.right = removeMinNode(root.right);
            minNode.left = root.left;
            root.left = root.right = null;
            return minNode;
        }
        
        return root;
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
        inOrder(root.right, resultList);
    }
}
