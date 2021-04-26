package stack;

/**
 * 栈接口定义。
 * 栈是一种元素先进后出的结构，其本质上是一种操作受到限制的线性表结构，即所有的操作的只能在线性表的一端进行，只能在栈顶添加元素，也只能在栈顶取元素。
 * 
 */
public interface Stack<E> {

    /**
     * 将元素 e 压入栈中，时间复杂度 O(1) （均摊）
     * 
     * @param e 待压入栈中的元素
     */
    public void push(E e);

    /**
     * 弹出栈顶元素，时间复杂度 O(1) （均摊）
     * 
     * @return 栈顶元素
     */
    public E pop();

    /**
     * 获取栈顶元素，但是不弹出。时间复杂度 O(1)
     * 
     * @return 栈顶元素
     */
    public E peek();

    /**
     * 获取栈中元素个数。时间复杂度 O(1)
     * 
     * @return 栈的元素个数
     */
    public int size();

    /**
     * 判断栈是否为空，如果栈为空，则返回 true；否则，返回 false。时间复杂度 O(1)
     * 
     * @return 如果栈为空，则返回 true；否则，返回 false。
     */
    public boolean isEmpty();
}
