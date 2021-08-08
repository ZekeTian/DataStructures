package linkedlist;

public class DoubleLinkedList<E> implements List<E> {
    
    private class Node {
        Node pre;
        Node next;
        E val;
        
        Node(Node pre, Node next, E val) {
            this.pre = pre;
            this.next = next;
            this.val = val;
        }
    }
    
    private Node head = null; // 头节点，并不存储真实数据
    private Node tail = null; // 尾节点
    private int size = 0;

    public DoubleLinkedList() {
        head = new Node(null, null, null);
        tail = head;
        size = 0;
    }
    
    @Override
    public void add(E e) {
        Node node = new Node(tail, null, e);
        tail.next = node;
        tail = node;
        ++size;
    }

    @Override
    public void add(int index, E e) {
        Node node = getNode(index);
        Node preNode = node.pre;
        
        Node newNode = new Node(preNode, node, e);
        preNode.next = newNode;
        node.pre = newNode;
        ++size;
    }

    @Override
    public void clear() {
        head.next = null;
        tail = head;
        size = 0;
    }

    @Override
    public boolean contains(E e) {
        return getNode(e) != null;
    }

    @Override
    public E get(int index) {
        return getNode(index).val;
    }

    @Override
    public E set(int index, E e) {
        Node node = getNode(index);
        E oldVal = node.val;
        node.val = e;
        
        return oldVal;
    }

    @Override
    public boolean isEmpty() {
        return 0 == size;
    }

    @Override
    public E remove(int index) {
        Node node = getNode(index);
        Node nextNode = node.next;
        Node preNode = node.pre;
        
        preNode.next = nextNode;
        if (null != nextNode) {
            nextNode.pre = preNode;
        }
        --size;
        
        return node.val;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * 获取 index 处的节点
     */
    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IllegalArgumentException("Illegal Index");
        }
        
        if (index < size / 2) {
            // 从头开始遍历，寻找 index 处的节点
            Node cur = head.next;
            
            for (int i = 0; i < index; ++i) {
                cur = cur.next;
            }
            
            return cur;
        }
        
        // 从尾开始遍历，寻找 index 处的节点
        Node cur = tail;
        for (int i = size - 1; i > index; --i) {
            cur = cur.pre;
        }
        return cur;
    }
    
    /**
     * 获取第一个值为 e 的节点
     */
    private Node getNode(E e) {
        Node cur = head.next;
        
        while (null != cur) {
            if (cur.val.equals(e)) {
                return cur;
            }
            
            cur = cur.next;
        }
        
        return null;
    }
    
    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("head");
        if (isEmpty()) {
            builder.append(" -> null");
        }
        
        Node cur = head.next;
        
        while (cur != null) {
            builder.append(" -> " + cur.val);
            cur = cur.next;
        }
        
        return builder.toString();
    }
}
