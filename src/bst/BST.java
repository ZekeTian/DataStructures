package bst;

import java.util.LinkedList;
import java.util.Stack;

/**
 * 二分搜索树结构
 * 
 * @author zeke
 *
 */
public class BST<E extends Comparable<E>> {

    /**
     * 树节点的定义
     */
    private class Node {
        E e; // 元素
        Node left; // 左子树
        Node right; // 右子树

        public Node(E e) {
            this.e = e;
            left = null;
            right = null;
        }
    }

    /**
     * 根节点
     */
    private Node root;
    private int size;

    public BST() {
        root = null;
        size = 0;
    }

    /**
     * 获取二分搜索树的大小
     */
    public int size() {
        return size;
    }

    /**
     * 判断二分搜索树是否为空
     * 
     * @return 如果二分搜索树为空，则返回 true；否则，返回 false
     */
    public boolean isEmpty() {
        return 0 == size;
    }

    /**
     * 向二分搜索树中添加元素 e
     * 
     * @param e
     */
    public void add(E e) {
        if (null == root) {
            root = new Node(e);
            ++size;
            return;
        }

        addNode(root, e);
    }

    /**
     * 向根为 root 的二分搜索树中添加元素 e，第一种递归方式，递归到 null
     * 
     * @param root  二分搜索树的根
     * @param e     待添加元素
     * @return      返回添加元素后的二分搜索树的根节点
     */
    private Node addNode(Node root, E e) {
        // 根为空，则不继续递归寻找 e 的插入位置
        if (null == root) {
            ++size;
            return new Node(e);
        }

        // 判断继续递归寻找 e 的方向
        if (e.compareTo(root.e) > 0) {
            root.right = addNode(root.right, e);
        } else if (e.compareTo(root.e) < 0) {
            root.left = addNode(root.left, e);
        }

        return root;
    }

    /**
     * 向根为 root 的二分搜索树中添加元素 e，第二种递归方式，递归到叶子节点
     * 
     * @param root  二分搜索树的根
     * @param e     待添加元素
     */
    private void addNode2(Node root, E e) {
        // 递归终止条件
        if (null == root.left && e.compareTo(root.e) < 0) {
            ++size;
            root.left = new Node(e);
            return;
        } else if (null == root.right && e.compareTo(root.e) > 0) {
            ++size;
            root.right = new Node(e);
            return;
        }

        // 判断继续递归寻找 e 的方向
        if (e.compareTo(root.e) < 0) {
            addNode2(root.left, e);
        } else if (e.compareTo(root.e) > 0) {
            addNode2(root.right, e);
        }
    }

    /**
     * 判断二分搜索树中是否含有元素 e
     * 
     * @param e
     */
    public boolean contains(E e) {
        if (null == root) {
            throw new IllegalArgumentException("BST is empty!");
        }
        
        return contains(root, e);
    }

    private boolean contains(Node root, E e) {
        if (null == root) {
            return false;
        }

        if (e.compareTo(root.e) == 0) {
            return true;
        } else if (e.compareTo(root.e) < 0) {
            return contains(root.left, e);
        } else {
            return contains(root.right, e);
        }
    }

    /**
     * 在二分搜索树中删除指定元素 e
     * 
     * @param e
     */
    public void remove(E e) {
        if (!contains(e)) {
            return;
        }

        root = remove(root, e);
    }
    

    /**
     * 在以 root 为根节点的二分搜索树中，删除指定值 e，并返回删除后的二分搜索树的根节点。
     * 第一种方式，递归到 null
     * 
     * @param root  二分搜索树的根节点
     * @param e     待删除的值
     * @return
     */
    private Node remove(Node root, E e) {
        if (null == root) {
            return null;
        }
        
        if (e.compareTo(root.e) > 0) {
            root.right = remove(root.right, e);
            return root;
        } else if (e.compareTo(root.e) < 0) {
            root.left = remove(root.left, e);
            return root;
        } else {
            // 判断 root 的左右子树是否为空
            if (null == root.left) { // 左子树为空，则返回右子树的根节点即可
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
            
            // null != root.left && null != root.right 时，从右子树中选择最小值代替 root（或者从左子树中选择最大值代替 root）
            Node min = min(root.right);
            min.right = removeMin(root.right);
            min.left = root.left;
            root.left = root.right = null;
            return min;
        }
    }

    /**
     * 在以 root 为根节点的二分搜索树中，删除指定值 e，并返回删除后的二分搜索树的根节点。
     * 第二种方式，递归到叶子节点
     * 
     * @param root  二分搜索树的根节点
     * @param e     待删除的值
     * @return
     */
    private Node remove1(Node root, E e) {
        if (e.compareTo(root.e) == 0) {
            if (null == root.left && null == root.right) {
                --size;
                return null;
            }

            // 判断右子树是否为空，如果不为空，则从右子树中选择最小值，从而代替当前 root 的位置
            if (null != root.right) {
                Node min = min(root.right);
                min.right = removeMin(root.right);
                min.left = root.left;
                return min;
            }

            // 右子树为空，则直接将左子树的根节点返回即可
            --size;
            Node ret = root.left;
            root.left = null;
            return ret;
        }

        if (e.compareTo(root.e) > 0) {
            root.right = remove(root.right, e);
        } else if (e.compareTo(root.e) < 0) {
            root.right = remove(root.left, e);
        }

        return root;
    }

    /**
     * 删除二分搜索树中的最小值，并将其返回
     * 
     * @return 二分搜索树中的最小值
     */
    public E removeMin() {
        E min = min();
        root = removeMin(root);

        return min;
    }

    /**
     * 删除以 root 为根节点的二分搜索树中的最小值，并返回删除后的二分搜索树的根节点
     * @param root 二分搜索树的根节点
     * @return 删除最小值后的二分搜索树的根节点
     */
    private Node removeMin(Node root) {
        if (null == root.left) {
            Node ret = root.right;
            root.right = null;
            --size;
            return ret;
        }

        root.left = removeMin(root.left);

        return root;
    }

    /**
     * 获取二分搜索树中的最小值
     * 
     * @return 二分搜索树中的最小值
     */
    public E min() {
        if (null == root) {
            throw new IllegalArgumentException("BST is empty!");
        }

        return min(root).e;
    }

    /**
     * 获取二分搜索树中的最小值
     * 
     * @param root 二分搜索树的根节点
     * @return 二分搜索树中的最小值
     */
    private Node min(Node root) {
        if (null == root.left) {
            return root;
        }
        return min(root.left);
    }

    /**
     * 获取二分搜索树中的最大值
     * 
     * @return 二分搜索树中的最大值
     */
    public E max() {
        if (null == root) {
            throw new IllegalArgumentException("BST is empty!");
        }

        return max(root).e;
    }

    /**
     * 获取以 root 为根节点的二分搜索树中的最大值
     * @param root
     * @return
     */
    private Node max(Node root) {
        if (null == root.right) {
            return root;
        }

        return max(root.right);
    }

    /**
     * 删除二分搜索树中的最大值，并将其返回
     * 
     * @return 二分搜索树中的最大值
     */
    public E removeMax() {
        E max = max();
        root = removeMax(root);

        return max;
    }

    /**
     * 删除以 root 为根节点的二分搜索树中的最大值，并返回删除后的二分搜索树的根节点
     * @param root 二分搜索树的根节点
     * @return 删除最大值后的二分搜索树的根节点
     */
    public Node removeMax(Node root) {
        if (null == root.right) { // 找到最大值
            // 将最大值与左子树断开，并返回左子树的根节点
            Node ret = root.left;
            root.left = null; // 将最大值与左子树断开
            --size;
            return ret; // 返回左子树的根节点
        }

        // 将右子树中的最大值删除之后，将右子树的根节点重新与 root 进行连接
        root.right = removeMax(root.right);

        return root;
    }

    /**
     * 二分搜索树的层序遍历
     */
    public void levelOrder() {
        LinkedList<Node> list = new LinkedList<Node>();
        list.add(root);
        
        while (!list.isEmpty()) {
            Node node = list.remove();
            
            System.out.println(node.e);
            
            if (null != node.left) {
                list.add(node.left);
            }
            if (null != node.right) {
                list.add(node.right);
            }
        }
    }
    
    /**
     * 先序遍历
     */
    public void preOrder() {
        preOrder(root);
    }

    /**
     * 先序遍历以 root 为根节点的二分搜索树  
     * @param root
     */
    private void preOrder(Node root) {
        if (null == root) {
            return;
        }

        System.out.println(root.e);
        preOrder(root.left);
        preOrder(root.right);
    }

    /**
     * 先序遍历非递归实现
     */
    public void preOrderNR() {
        if (null == root) {
            return;
        }
        
        Stack<Node> stack = new Stack<Node>();
        stack.push(root);
        
        while (!stack.isEmpty()) {
            Node node = stack.pop();
            System.out.println(node.e);
            
            // 先序：根、左、右
            if (null != node.right) { // 右子树先进，从而在栈中后出
                stack.push(node.right);
            }
            if (null != node.left) { // 左子树后进，从而在栈中先出
                stack.push(node.left);
            }
        }
    }

    /**
     * 中序遍历
     */
    public void inOrder() {
        inOrder(root);
    }

    /**
     * 中序遍历以 root 为根节点的二分搜索树  
     * 
     * @param root
     */
    private void inOrder(Node root) {
        if (null == root) {
            return;
        }
        inOrder(root.left);
        System.out.println(root.e);
        inOrder(root.right);
    }

    /**
     * 后序遍历
     */
    public void postOrder() {
        postOrder(root);
    }

    /**
     * 后序遍历以 root 为根节点的二分搜索树  
     * 
     * @param root
     */
    public void postOrder(Node root) {
        if (null == root) {
            return;
        }

        postOrder(root.left);
        postOrder(root.right);
        System.out.println(root.e);
    }
}
