package hashtable;

import java.util.LinkedList;
import java.util.TreeMap;

/**
 * 链地址法解决哈希冲突，底层使用 LinkedList ，对 key 无特殊要求。
 */
public class LinkedHashTable<K, V> implements HashTable<K, V> {

    private class Node {
        K key;
        V val;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }
    }

    /**
     * 哈希表容量
     */
    private static final int[] CAPACITY_ARR = { 53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593, 49157, 98317,
            196613, 393241, 786433, 1572869, 3145739, 6291469, 12582917, 25165843, 50331653, 100663319, 201326611,
            402653189, 805306457, 1610612741 };

    /**
     * 平均每个哈希桶含有的元素个数上限
     */
    private static final int UPPER = 2; // 10;

    /**
     * 平均每个哈希桶含有的元素个数下限
     */
    private static final int LOWER = 1; // 2;

    /**
     * 存储数据
     */
    private LinkedList<Node>[] hashtable;

    /**
     * 哈希表大小
     */
    private int size;

    /**
     * 哈希表当前容量，哈希桶（Hash Bucket）的数量。
     */
    private int capacity;

    /**
     * 哈希表当前容量大小在 CAPACITY 数组中下标，如：当前容量为 53，则 capacityIndex 为 0。
     */
    private int capacityIndex;

    public LinkedHashTable() {
        size = 0;
        capacityIndex = 0;
        capacity = CAPACITY_ARR[capacityIndex];
        hashtable = new LinkedList[capacity];

        for (int i = 0; i < capacity; ++i) {
            hashtable[i] = new LinkedList<Node>();
        }
    }

    @Override
    public void put(K key, V val) {
        Node node = getNode(key);
        if (null != node) { // 已经存在 key，则直接更新该值即可
            node.val = val;
            return;
        }

        // 不存在 key，则添加新值
        hashtable[hash(key)].add(new Node(key, val));
        ++size;

        if (size >= UPPER * capacity && capacityIndex + 1 < CAPACITY_ARR.length) {
            System.out.println("before resize(put), capacity is: " + capacity + ", size: " + size);
            resize(CAPACITY_ARR[++capacityIndex]);
            System.out.println("after resize(put), capacity is: " + capacity);
        }
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        if (null == node) {
            return null;
        }

        return node.val;
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if (null == node) { // 不包含 key 对应的元素，无需删除
            return null;
        }

        // 删除 key 对应的元素
        hashtable[hash(key)].remove(node);
        --size;

        if (size <= LOWER * capacity && capacityIndex - 1 >= 0) {
            System.out.println("before resize(remove), capacity is: " + capacity + ", size: " + size);
            resize(CAPACITY_ARR[--capacityIndex]);
            System.out.println("after resize(remove), capacity is: " + capacity);
        }

        return node.val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(K key) {
        return null != getNode(key);
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    /**
     * 获取 key 对应的节点。如果能获取到，则返回该节点；否则，返回 null。
     * 
     * @param key
     * @return key 对应的节点。如果能获取到，则返回该节点；否则，返回 null。
     */
    private Node getNode(K key) {
        LinkedList<Node> list = hashtable[hash(key)];

        for (Node e : list) {
            if (e.key.equals(key)) {
                return e;
            }
        }

        return null;
    }

    private void resize(int newCapacity) {
        int oldCapacity = capacity;
        capacity = newCapacity;

        LinkedList<Node>[] newHashtable = new LinkedList[newCapacity];
        for (int i = 0; i < newCapacity; ++i) {
            newHashtable[i] = new LinkedList<Node>();
        }

        // 将旧哈希表中的元素复制到扩容后的哈希表中
        for (int i = 0; i < oldCapacity; ++i) {
            LinkedList<Node> list = hashtable[i];

            for (Node e : list) {
                newHashtable[hash(e.key)].add(e);
            }
        }

        hashtable = newHashtable;
    }
}
