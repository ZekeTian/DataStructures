package map;

public interface Map<K, V> {
    
    /**
     * 判断 map 中是否存在 key
     * @return 如果存在，则返回 true；否则，返回 false
     */
    public boolean contains(K key);
    
    /**
     * 将指定的值与此映射中的指定键关联（可选操作）。如果此映射以前包含一个该键的映射关系，则用指定值替换旧值。
     * @param key   与指定值关联的键
     * @param value 与指定键关联的值 
     */
    public void put(K key, V value);
    
    /**
     * 如果存在一个键的映射关系，则将其从此映射中移除，并且返回该键关联的值
     * @param key
     * @return
     */
    public V remove(K key);
    
    
    /**
     * 返回指定键所映射的值；如果此映射不包含该键的映射关系，则返回 null。
     * @param key
     */
    public V get(K key);
    
    /**
     * 判断 Map 是否为空
     * 
     * @return 如果此映射未包含键-值映射关系，则返回 true。否则，返回 false
     */
    public boolean isEmpty();
    
    /**
     * 返回此映射中的键-值映射关系数
     * @return
     */
    public int size();
    
}
