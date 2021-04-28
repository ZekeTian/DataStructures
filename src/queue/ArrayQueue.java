package queue;

import java.util.ArrayList;
import java.util.Iterator;

public class ArrayQueue<E> implements Queue<E> {

    private ArrayList<E> array;

    public ArrayQueue() {
        array = new ArrayList<E>();
    }

    @Override
    public void enqueue(E e) {
        array.add(e);
    }

    @Override
    public E dequeue() {
        if (isEmpty()) {
            throw new IllegalArgumentException("queue is empty!");
        }

        return array.remove(0);
    }

    @Override
    public E getFront() {
        if (isEmpty()) {
            throw new IllegalArgumentException("queue is empty!");
        }

        return array.get(0);
    }

    @Override
    public int size() {
        return array.size();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("head[");

        for (E e : array) {
            builder.append(" " + e);
        }

        builder.append(" ]tail");

        return builder.toString();
    }
}
