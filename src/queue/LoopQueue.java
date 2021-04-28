package queue;

/**
 * 循环队列，基于数组实现
 */
public class LoopQueue<E> implements Queue<E> {

    /**
     * 存储数据
     */
    private E[] data;

    /**
     * 队列中的元素个数
     */
    private int size;

    /**
     * 队首位置
     */
    private int front;

    /**
     * 队尾位置
     */
    private int tail;

    public LoopQueue() {
        this(10);
    }

    public LoopQueue(int capacity) {
        data = (E[]) new Object[capacity + 1];
        size = 0;
        front = tail = 0;
    }

    @Override
    public void enqueue(E e) {
        if ((tail + 1) % data.length == front) {
            // 队列已满，需要扩容
            resize(2 * data.length);
        }

        data[tail] = e;
        tail = (tail + 1) % data.length;
        ++size;
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty!");
        }

        E e = data[front];
        front = (front + 1) % data.length;
        --size;

        // 使用懒缩容策略，避免震荡
        // data.length / 4 > size ===> data.length > 4 * size
        if (data.length > 4 * size /* 队列元素个数比容量的四分之一还要小 */
                && data.length / 2 > 0 /* 缩容后的队列长度不为 0 */ ) {
            resize(data.length / 2);
        }

        return e;
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Queue is empty!");
        }

        return data[front];
    }

    @Override
    public int size() {
        // 方式一：直接返回 size
        return size;

        // 方式二：通过队首、队尾指针计算 size
        // return (tail - front + data.length) % data.length;
    }

    @Override
    public boolean isEmpty() {
        return front == tail; // front == tail 时，队列为空
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("head[");

        // 第一种遍历方式
        //        for (int i = front; i % data.length != tail; ++i) {
        //            builder.append(" " + data[i % data.length]);
        //        }

        // 第二种遍历方式
        for (int i = 0; i < size; ++i) {
            builder.append(" " + data[(front + i) % data.length]);
        }

        builder.append(" ]tail");

        return builder.toString();
    }

    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity + 1];

        // 将原来队列的数据复制到新队列中

        //        int count = 0;
        //        for (int i = front; i % data.length != tail; ++i) {
        //            newData[count++] = data[i % data.length];
        //        }

        for (int i = 0; i < size; ++i) {
            newData[i] = data[(front + i) % data.length];
        }

        front = 0;
        tail = size;

        data = newData;
    }
}
