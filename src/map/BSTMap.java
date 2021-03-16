package map;

public class BSTMap<K extends Comparable<K>, V> implements Map<K, V> {
    private class Node {
        K key;
        V value;
        Node left;
        Node right;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
            left = null;
            right = null;
        }
    }

    private int size;
    private Node root;

    public BSTMap() {
        root = null;
        size = 0;
    }

    @Override
    public boolean contains(K key) {
        return getNode(root, key) != null;
    }

    @Override
    public void put(K key, V value) {
        if (null == root) {
            ++size;
            root = new Node(key, value);
            return;
        }

        // Map 中存在 key，则对其进行更新
        Node node = getNode(root, key);
        if (null != node) {
            node.value = value;
            return;
        }

        // Map 中不存在 key，则将其放入 map 中
        add(root, key, value);
    }

    /**
     * 在以 root 为根节点的二分搜索树中，添加键值对元素 (key, value)
     * @param root
     * @param key
     * @param value
     * @return 返回添加元素后的二分搜索树的根节点
     */
    private Node add(Node root, K key, V value) {
        if (null == root) {
            ++size;
            return new Node(key, value);
        }

        if (key.compareTo(root.key) < 0) {
            root.left = add(root.left, key, value);
        } else if (key.compareTo(root.key) > 0) {
            root.right = add(root.right, key, value);
        }
        return root;
    }

    @Override
    public V remove(K key) {
        if (null == root) {
            throw new IllegalArgumentException("BST Map is empty!");
        }

        Node node = getNode(root, key);

        if (null == node) {
            return null;
        }

        root = removeNode(node, key);

        return node.value;
    }

    /**
     * 在以 root 为根节点的二分搜索树中，删除 key 对应的元素
     * @param key
     * @return 返回删除元素后的二分搜索树的根节点
     */
    private Node removeNode(Node root, K key) {
        if (null == root) {
            return null;
        }

        if (0 == key.compareTo(root.key)) {
            // 找到 key 对应的元素，终止递归，并进行删除操作
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
            // root 的左右子树均存在，则从左子树中取出最大值，代替 root 的位置
            Node max = maxNode(root.left);
            max.left = removeMax(root.left);
            max.right = root.right;
            root.left = root.right = null;
            return max;
        } else if (key.compareTo(root.key) < 0) {
            root.left = removeNode(root.left, key);
            return root;
        } else { // key.compareTo(root.key) > 0
            root.right = removeNode(root.right, key);
            return root;
        }
    }

    /**
     * 在以 root 为根节点的二分搜索树中删除最大的元素
     * @param   root
     * @return  返回删除最大值后的二分搜索树的根节点
     */
    private Node removeMax(Node root) {
        if (null == root.right) {
            Node ret = root.left;
            root.left = null;
            --size;
            return ret;
        }

        root.right = removeMax(root.right);
        return root;
    }

    /**
     * 在以 root 为根节点的二分搜索中获取最大的元素
     * @param root
     * @return 最大的元素
     */
    private Node maxNode(Node root) {
        if (null == root.right) {
            return root;
        }

        return maxNode(root.right);
    }

    @Override
    public V get(K key) {
        Node node = getNode(root, key);
        return node == null ? null : node.value;
    }

    /**
     * 在以 root 为根节点的二分搜索树中，获取键值为 key 的节点
     * @param root  根节点 
     * @param key   待获取的键值
     * @return      如果 map 中包含 key，则返回到 key 对应的节点；否则，返回 null
     */
    private Node getNode(Node root, K key) {
        if (null == root) {
            return null;
        }

        if (key.compareTo(root.key) < 0) {
            return getNode(root.left, key);
        } else if (key.compareTo(root.key) > 0) {
            return getNode(root.right, key);
        } else {
            return root; // 找到指定值
        }
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

}
