package avl;

import set.Set;

public class AVLSet<E extends Comparable<E>> implements Set<E> {

    /**
     * 将 map 中的 key 作为集合中的元素，value 设置为 null，从而使用 map 表示 set
     */
    private AVLMap<E, Object> map;
    
    @Override
    public void add(E e) {
        map.put(e, null);
    }

    @Override
    public boolean contains(E e) {
        return map.contains(e);
    }

    @Override
    public void remove(E e) {
        map.remove(e);
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }
}
