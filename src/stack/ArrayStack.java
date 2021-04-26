package stack;

import java.util.ArrayList;

public class ArrayStack<E> implements Stack<E> {
    private ArrayList<E> array;

    public ArrayStack() {
        array = new ArrayList<E>();
    }

    @Override
    public void push(E e) {
        array.add(e);
    }

    @Override
    public E pop() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty!");
        }

        return array.remove(array.size() - 1);
    }

    @Override
    public E peek() {
        if (isEmpty()) {
            throw new IllegalArgumentException("stack is empty!");
        }

        return array.get(array.size() - 1);
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
        builder.append("bottom[");

        for (E e : array) {
            builder.append(" " + e);
        }
        builder.append(" ]top");

        return builder.toString();
    }
}
