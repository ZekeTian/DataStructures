package linkedlist;

public interface List<E> {

    /**
     * 向列表的尾部添加指定的元素
     * 
     * @param e 待添加的元素
     */
    public void add(E e);

    /**
     * 在列表的指定位置插入指定元素
     *  
     * @param index 待添加元素的位置
     * @param e 待添加的元素
     */
    public void add(int index, E e);

    /**
     * 从列表中移除所有元素
     */
    public void clear();

    /**
     * 如果列表包含指定的元素 e，则返回 true；否则，返回 false。
     * @param e 要测试列表中是否存在的元素
     * @return 如果列表包含指定的元素，则返回 true 
     */
    public boolean contains(E e);

    /**
     * 返回列表中指定位置的元素。
     * @param index 指定位置
     * @return index 处的元素
     */
    public E get(int index);

    /**
     * 用指定元素替换列表中指定位置的元素
     * @param index 指定位置
     * @param e 新元素
     * @return 指定位置的旧元素
     */
    public E set(int index, E e);

    /**
     * 判断列表是否为空
     * @return 如果列表不包含元素，则返回 true
     */
    public boolean isEmpty();

    /**
     * 移除列表中指定位置的元素
     * @param index 指定位置
     * @return 指定位置被删除的元素的值
     */
    public E remove(int index);

    /**
     * 返回列表中的元素数
     * @return 列表中的元素数
     */
    public int size();
}
