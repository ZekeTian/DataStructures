package heap;

public class PriorityQueue<E extends Comparable<E>> {
    private MaxHeap<E> heap;
    
    public PriorityQueue() {
        heap = new MaxHeap<E>();
    }
    
    public void enqueue(E e) {
        heap.add(e);
    }

    public E dequeue() {
        return heap.extractMax();
    }
    
    public E getFront() {
        return heap.findMax();
    }
    
    public int size() {
        return heap.size();
    }
    
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    

}
