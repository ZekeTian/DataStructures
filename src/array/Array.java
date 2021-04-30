package array;

/**
 * 动态数组实现
 */
public class Array<E> {

    /**
     * 存储数据
     */
    private E[] data;

    /**
     * 数组中的元素个数
     */
    private int size;

    public Array() {
        this(10);
    }

    public Array(int capacity) {
        data = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * 将指定的元素插入到数组中的指定位置。
     * 
     * @param index 插入的指定位置
     * @param e     插入的元素
     */
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IllegalArgumentException("Illegal Index!");
        }

        if (size == getCapacity()) {
            System.out.println("befroe add, capacity: " + getCapacity());
            // 数组已经放满，需要扩容
            resize(2 * getCapacity());
            System.out.println("after add, capacity: " + getCapacity());
        }

        for (int i = size - 1; i >= index; --i) {
            data[i + 1] = data[i];
        }

        data[index] = e;
        ++size;
    }

    /**
     * 在数组的头部添加元素 e
     * 
     * @param e 待添加的元素
     */
    public void addFirst(E e) {
        add(0, e);
    }

    /**
     * 在数组的尾部添加元素 e
     * 
     * @param e 待添加的元素
     */
    public void addLast(E e) {
        add(size, e);
    }

    /**
     * 判断数组中是否包含指定元素 e
     * 
     * @param e 测试此数组中是否存在的元素
     * @return 如果数组中包含指定的元素，则返回 true；否则，返回 false。
     */
    public boolean contains(E e) {
        return -1 != find(e);
    }

    /**
     * 在数组中寻找指定元素，并返回指定元素的索引。
     * 
     * @param e 待查找的元素
     * @return 如果在数组中能找到指定元素，则返回该元素首次出现时的索引；否则，返回 -1。
     */
    public int find(E e) {

        for (int i = 0; i < size; ++i) {
            if (data[i].equals(e)) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 获取数组中指定位置的元素
     * 
     * @param index 指定位置
     * @return 数组中 index 处的元素
     */
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Illegal Index!");
        }

        return data[index];
    }

    /**
     * 获取数组的容量
     * 
     * @return 数组的容量
     */
    public int getCapacity() {
        return data.length;
    }

    /**
     * 获取数组中的元素个数
     * 
     * @return 数组中的元素个数
     */
    public int size() {
        return size;
    }

    /**
     * 判断数组是否为空，如果数组为空，则返回 true；否则，返回 false。
     * 
     * @return 如果数组为空，则返回 true；否则，返回 false。
     */
    public boolean isEmpty() {
        return 0 == size;
    }

    /**
     * 删除数组中指定位置的元素。
     * 
     * @param index 待删除元素的索引
     * @return 待删除的元素
     */
    public E remove(int index) {
        if (isEmpty()) {
            throw new IllegalArgumentException("Array is Empty!");
        }

        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Illegal Index!");
        }

        E e = data[index];

        for (int i = index + 1; i < size; ++i) {
            data[i - 1] = data[i];
        }

        data[size - 1] = null;
        --size;

        // 使用懒缩容策略，即当实际元素个数小于容量的 1/4 时，才进行缩容；而不是当元素个数小于容量的 1/2 时，就立马缩容。
        // 采取这种策略，可以避免当反复添加、删除元素时出现震荡（即不断重复 resize）
        // size < capacity / 4 && capacity / 2 > 0
        if (4 * size < getCapacity() /* 元素数量小于容量的四分之一 */
                && getCapacity() / 2 > 0 /* 新容量大于 0 */) {
            System.out.println("befroe remove, capacity: " + getCapacity());
            resize(getCapacity() / 2);
            System.out.println("after remove, capacity: " + getCapacity());
        }

        return e;
    }

    /**
     * 删除数组中首次出现的指定元素
     * 
     * @param e 待删除的指定元素
     */
    public void removeElement(E e) {
        int index = -1;
        for (int i = 0; i < size; ++i) {
            if (data[i].equals(e)) {
                index = i;
                break;
            }
        }

        if (-1 != index) {
            remove(index);
        }
    }

    /**
     * 删除数组的头部元素
     * 
     * @return 数组的头部元素
     */
    public E removeFirst() {
        return remove(0);
    }

    /**
     * 删除数组的尾部元素
     * 
     * @return 数组的尾部元素
     */
    public E removeLast() {
        return remove(size - 1);
    }

    /**
     * 将数组中指定位置的元素更新为指定的新值 e
     * 
     * @param index 待更新的元素位置
     * @param e     元素新值
     */
    public void set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Illegal Index!");
        }

        data[index] = e;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        builder.append("[");
        for (int i = 0; i < size; ++i) {
            builder.append(" " + data[i]);
        }
        builder.append(" ]");

        return builder.toString();
    }

    /**
     * 将数组的容量调整成指定大小
     * 
     * @param newCapacity 指定的新数组容量
     */
    private void resize(int newCapacity) {
        E[] newData = (E[]) new Object[newCapacity];

        for (int i = 0; i < size; ++i) {
            newData[i] = data[i];
        }

        data = newData;
    }
}
