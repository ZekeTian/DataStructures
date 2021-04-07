package avl;

import map.Map;

public class AVLMap<K extends Comparable<K>, V> implements Map<K, V> {
    private AVLTree<K, V> map;
    
    public AVLMap() {
        map = new AVLTree<K, V>();
    }

    @Override
    public boolean contains(K key) {
        return map.contains(key);
    }

    @Override
    public void put(K key, V value) {
        map.put(key, value);
    }

    @Override
    public V remove(K key) {
        return map.remove(key);
    }

    @Override
    public V get(K key) {
        return map.get(key);
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public int size() {
        return map.size();
    }

}
