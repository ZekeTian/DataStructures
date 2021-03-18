package heap;

import java.util.ArrayList;
import java.util.List;

public class MaxHeap<E extends Comparable<E>> {
    private List<E> data;

    public MaxHeap() {
        data = new ArrayList<E>();
    }

    public int getSize() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

}
