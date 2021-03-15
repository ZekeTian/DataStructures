package map;

import java.util.LinkedList;
import java.util.List;

public class LinkedListMap<K, V> implements Map<K, V> {

    private class Node {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }

    private List<Node> list;

    public LinkedListMap() {
        list = new LinkedList<Node>();
    }

    @Override
    public boolean contains(K key) {
        return get(key) != null;
    }

    @Override
    public void put(K key, V value) {
        // 判断 Map 中是否含有 key，如果有 key ，则直接用 value 对其进行更新即可
        Node node = getNode(key);
        if (node != null) {
            node.value = value;
            return;
        }

        // Map 中没有 key，则直接存储
        list.add(new Node(key, value));
    }

    @Override
    public V remove(K key) {
        Node node = getNode(key);
        if (null != node) {
            list.remove(node);
            return node.value;
        }
        return null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    /**
     * 根据 key 获取完整的 Node 值
     * @param key
     * @return
     */
    private Node getNode(K key) {
        for (Node n : list) {
            if (n.key.equals(key)) {
                return n;
            }
        }

        return null;
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public int size() {
        return list.size();
    }
}
