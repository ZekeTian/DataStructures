package linkedlist;

/**
 * 链表实现
 */
public class LinkedList<E> implements List<E> {

    /**
     * 链表节点定义
     */
    private class Node {
        E e; // 元素值
        Node next; // 下一个链表节点

        public Node(E e) {
            this(e, null);
        }

        public Node(E e, Node next) {
            this.e = e;
            this.next = next;
        }
    }

    /**
     * 头节点
     */
    private Node head;

    /**
     * 链表大小
     */
    private int size;

    public LinkedList() {
        head = new Node(null);
    }

    @Override
    public void add(E e) {
        Node cur = head;

        while (null != cur.next) {
            cur = cur.next;
        }

        // 遍历到最后一个节点
        Node newNode = new Node(e);
        newNode.next = cur.next;
        cur.next = newNode;

        // 上面三行可以等价于下面一行
        //        cur.next = new Node(e);

        ++size;
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index is illegal");
        }

        int count = 0;
        Node cur = head;

        while (count < index) {
            cur = cur.next;
            ++count;
        }

        Node newNode = new Node(e);
        newNode.next = cur.next;
        cur.next = newNode;
        ++size;
    }

    @Override
    public void clear() {
        head.next = null;
        size = 0;
    }

    @Override
    public boolean contains(E e) {
        Node cur = head;

        while (null != cur) {
            if (e.equals(cur.e)) {
                return true;
            }

            cur = cur.next;
        }

        return false;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index is illegal!");
        }

        return getNode(index).e;
    }

    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index is illegal!");
        }

        Node node = getNode(index);
        E oldE = node.e;
        node.e = e;

        return oldE;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Index is illegal!");
        }

        Node pre = getNode(index - 1); // 待删除节点的前一个节点
        Node delNode = pre.next; // 待删除的节点
        
        pre.next = delNode.next;
        delNode.next = null;
        --size;
        
        return delNode.e;
    }

    @Override
    public int size() {
        return size;
    }
    
    @Override
    public String toString() {
        
        StringBuilder builder = new StringBuilder("head");
        
        if (isEmpty()) {
            return builder.append("->null").toString();
        }
        
        Node cur = head.next;
        while (null != cur) {
            builder.append("->" + cur.e);
            cur = cur.next;
        }
        
        return builder.toString();
    }

    
    /**
     * 获取指定位置的链表节点
     * @param index 指定位置 
     * @return index 处的链表节点
     */
    private Node getNode(int index) {
        if (index < -1 || index >= size) { // index 为 -1 时，获取头节点
            throw new IllegalArgumentException("Index is illegal!");
        }

        int count = 0;
        Node cur = head;
        while (count <= index) {
            cur = cur.next;
            ++count;
        }

        return cur;
    }
}
