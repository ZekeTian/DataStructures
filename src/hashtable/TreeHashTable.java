package hashtable;

import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

/**
 * 链地址法解决哈希冲突，底层使用 TreeMap ，要求 key 必须是可比的。
 */
public class TreeHashTable<K extends Comparable<K>, V> implements HashTable<K, V> {
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
    private TreeMap<K, V>[] hashtable;

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

    public TreeHashTable() {
        size = 0;
        capacityIndex = 0;
        capacity = CAPACITY_ARR[capacityIndex];
        hashtable = new TreeMap[capacity];

        for (int i = 0; i < capacity; ++i) {
            hashtable[i] = new TreeMap<K, V>();
        }
    }

    @Override
    public void put(K key, V val) {
        hashtable[hash(key)].put(key, val);
        ++size;

        // 判断当前元素是否过多，若过多，则进行 resize 扩容
        if (size >= UPPER * capacity && capacityIndex + 1 < CAPACITY_ARR.length) {
            System.out.println("before resize(put), capacity is: " + capacity + ", size: " + size);
            resize(CAPACITY_ARR[++capacityIndex]);
            System.out.println("after resize(put), capacity is: " + capacity);
        }
    }

    @Override
    public V get(K key) {
        return hashtable[hash(key)].get(key);
    }

    @Override
    public V remove(K key) {
        TreeMap<K, V> map = hashtable[hash(key)];
        if (!map.containsKey(key)) {
            return null;
        }
        
        V val = map.remove(key);
        --size;
        
        if (size <= LOWER * capacity && capacityIndex - 1 >= 0) {
            System.out.println("before resize(remove), capacity is: " + capacity + ", size: " + size);
            resize(CAPACITY_ARR[--capacityIndex]);
            System.out.println("after resize(remove), capacity is: " + capacity);
        }
        
        return val;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(K key) {
        return hashtable[hash(key)].containsKey(key);
    }
    
    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    /**
     * 获取 key 的哈希值，即获取存放 key 的哈希桶索引
     * 
     * @param key
     * @return
     */
    private int hash(K key) {
        return (key.hashCode() & 0x7fffffff) % capacity; // key.hashCode 与 0x7fffffff 进行 & 操作，相当于是把 key.hashCode 的最高位变为 0 ，从而强制将其转换成正数（相当于取绝对值）
    }

    private void resize(int newCapacity) {
        int oldCapacity = capacity;
        capacity = newCapacity; // hash() 方法中需要对新容量进行取模，因此在此处需要更新 capacity

        // 扩容
        TreeMap<K, V>[] newHashtable = new TreeMap[newCapacity];
        for (int i = 0; i < newCapacity; ++i) {
            newHashtable[i] = new TreeMap<K, V>();
        }

        // 将旧哈希表中的元素逐个放进新哈希表中
        for (int i = 0; i < oldCapacity; ++i) {
            TreeMap<K, V> map = hashtable[i];
            Set<Entry<K, V>> entrySet = map.entrySet();
            for (Entry<K, V> entry : entrySet) {
                newHashtable[hash(entry.getKey())].put(entry.getKey(), entry.getValue());
            }
        }

        hashtable = newHashtable;
    }
}
