package set;

public interface Set<E> {

    /**
     * 向集合中添加元素 e
     * @param e
     */
    public void add(E e);

    /**
     * 判断集合中是否包含元素 e
     * 
     * @param e
     * 
     * @return 判断集合中是否包含元素 e
     */
    public boolean contains(E e);

    /**
     * 从集合中删除元素 e
     * 
     * @param e
     */
    public void remove(E e);

    /**
     * 获取集合的大小
     * 
     * @return 集合的大小
     */
    public int size();

    /**
     * 判断集合是否为空
     * @return 如果集合为空，则返回  true；否则，返回 false
     */
    public boolean isEmpty();
}
