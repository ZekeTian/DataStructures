package hashtable;

/*
 * 哈希表，最重要的是设计一个好的哈希函数，而一个好的哈希函数，其应当满足如下几个原则：
 * （1）一致性：如果 a==b，则 hash(a) == hash(b)
 * （2）高效性：计算高效简便
 * （3）均匀性：哈希值均匀分布，即“键”通过哈希函数得到的“索引”分布越均匀越好。
 * 
 * 常用数据类型的哈希函数设计：
 *  整数类型
 *      小整数：
 *          正整数，直接使用
 *          负整数，进行偏移，然后直接使用，如： -100 ~ 100  ——>（+100）  0 ~ 200
 *      大整数：对一个素数取模（对素数取模，可以使得哈希值分布更均匀）
 * 
 *  浮点数：直接转换成整数处理。因为浮点数、整数在计算机的存储都是 32 或 64 位的二进制串，所以可以直接将浮点数视作整数 ，然后用整数的方法进行处理。
 * 
 *  字符串：转换成整数处理。
 *      对于十进制整数 166，其可以视作：6 * 10^0 + 6 * 10^1 + 1 * 10^2，因此，对于字符串而言，其也可以用类似的方法。
 *      如，对于只含有大小写字母的字符串 "code"，其可以视作：e * 26^0 + d * 26^1 + o * 26^2 + c * 26^3。
 *      因为字符串中除了小写字母之外，还有大写字母，也可能含有其它字符，所以更进一步，可以将上面的 26 进制更换成 B 进制，
 *   则 "code" 可以视作：e * B^0 + d * B^1 + o * B^2 + c * B^3，hash(code) = (e * B^0 + d * B^1 + o * B^2 + c * B^3) % M。
 *      因为 B^n 可能会非常大，有可能会产生溢出，所以为了避免溢出，其哈希值可以采用如下方法：
 *      hash(code) = ((((c * B) + o) * B + d) * B + e) % M 
 *   => hash(code) = ((((c % M) * B + o) % M * B + d) % M * B + e) % M （避免相乘溢出，提前取模）
 * 
 * 哈希冲突常用解决方法
 *  （1）链地址法：是一种封闭地址类型的方法，即某个地址只能被某个固定的哈希值使用，不能被其它哈希值使用。
 *      具体处理措施，当发生哈希冲突时，将所有冲突的元素组织成链表或者红黑树
 *      
 *  （2）开放地址法：相对于链地址法而言，在开放地址法中，某个地址可以被其它哈希值使用。
 *      具体处理措施：
 *      1）线性探测，遇到冲突后，每次只向后移动一个位置
 *      2）平方探测，遇到冲突后，每次向后移动 n^2 个位置，如第 1 次移到 1 个位置，如果还发生冲突，则第 2 次移动 4 个位置；第 i 次移到 i^2 个位置。
 *      2）二次哈希，遇到冲突后，移到 hash2(key) 个位置，即冲突后，用第 2 个哈希函数再进行一次哈希，然后移到相应的位置。
 *   
 *  （3）再哈希法（Rehashing）
 *  
 *  （4）Coalesced Hashing：综合了链地址法和开放地址法两种方法的特点。
 * 
 */

/**
 * 哈希表接口定义
 */
public interface HashTable<K, V> {

    /**
     * 在哈希表中，存放 (key, val) 二元组。如果哈希表中存在 key 对应的元素，则用 val 更新其值；否则，直接放入即可。
     * 
     * @param key 键
     * @param val 值
     */
    public void put(K key, V val);

    /**
     * 获取哈希表中 key 对应的元素值
     * 
     * @param key 键
     * @return key 对应的元素值
     */
    public V get(K key);

    /**
     * 删除哈希表中 key 对应的元素值
     * 
     * @param key 键
     * @return 待删除元素值
     */
    public V remove(K key);

    /**
     * @return 哈希表的元素个数
     */
    public int size();

    /**
     * 如果哈希表中包含指定的键值 key，则返回 true；否则，返回 false。
     * 
     * @param key 要测试列表中是否存在的键值
     * @return 如果列表包含指定的键值，则返回 true
     */
    public boolean contains(K key);

    /**
     * 判断哈希表是否为空。
     * 
     * @return 如果哈希表为空，则返回 true；否则，返回 false。
     */
    public boolean isEmpty();
}
