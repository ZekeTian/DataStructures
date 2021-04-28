package queue;

public class Main {

    public static void main(String[] args) {
        //        Queue<Integer> queue = new ArrayQueue<Integer>();
        Queue<Integer> queue = new LoopQueue<Integer>();

        System.out.println("isEmpty: " + queue.isEmpty()); // true
        System.out.println(queue); // head[ ]tail

        queue.enqueue(10);
        queue.enqueue(20);
        queue.enqueue(30);
        queue.enqueue(40);

        System.out.println("isEmpty: " + queue.isEmpty()); // false
        System.out.println("size: " + queue.size()); // 4
        System.out.println(queue); // head[ 10 20 30 40 ]tail

        System.out.println("front: " + queue.getFront()); // 10
        System.out.println("dequeue: " + queue.dequeue()); // 10
        System.out.println("front: " + queue.getFront()); // 20

        System.out.println("size: " + queue.size()); // 3
        System.out.println(queue); // head[ 20 30 40 ]tail

        System.out.println("dequeue: " + queue.dequeue()); // 20
        System.out.println(queue); // head[ 30 40 ]tail
    }
}
