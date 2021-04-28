package queue;

/**
 * 队列接口定义。
 * 队列是一种元素先进先出的结构，其本质是一种操作受限的线性表结构，即操作只能在两端完成，只能从一端（队尾）添加元素，从另一端（队首）取出元素。
 */
public interface Queue<E> {

    /**
     * 将元素 e 加入队列，时间复杂度 O(1) （均摊）
     * 
     * @param e 待入队的元素
     */
    public void enqueue(E e);

    /**
     * 将队首元素出队，时间复杂度 O(1) （均摊）
     * 
     * @return 队首元素
     */
    public E dequeue();

    /**
     * 取出队首元素，时间复杂度 O(n)
     * 
     * @return 队首元素
     */
    public E getFront();

    /**
     * 获取队列中的元素个数，时间复杂度 O(1)
     * 
     * @return 队列中元素个数
     */
    public int size();

    /**
     * 判断队列是否为空，如果队列为空，则返回 true；否则，返回 false。时间复杂度 O(1)
     * 
     * @return 如果队列为空，则返回 true；否则，返回 false。
     */
    public boolean isEmpty();
}
