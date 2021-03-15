package set;

import java.util.LinkedList;
import java.util.List;

/**
 * 基于链表实现的集合 
 */
public class LinkedListSet<E> implements Set<E> {
    List<E> list;

    public LinkedListSet() {
        list = new LinkedList<E>();
    }
    
    @Override
    public void add(E e) {
        if (list.contains(e)) {
            return;
        }
        
        list.add(e);
    }

    @Override
    public boolean contains(E e) {
        return list.contains(e);
    }

    @Override
    public void remove(E e) {
        if (contains(e)) {
            list.remove(e);
        }
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }
}
