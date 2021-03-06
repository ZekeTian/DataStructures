package heap;

import java.util.Random;

public class Main {

    public static void main(String[] args) {
        
        MaxHeap<Integer> maxHeap = new MaxHeap<Integer>(); // 通过 add 方式创建的最大堆
        PriorityQueue<Integer> queue = new PriorityQueue<Integer>();
        Random random = new Random();
        int num = 10000;
        Integer[] arr = new Integer[num];
        for (int i = 0; i < num; ++i) {
            arr[i] = random.nextInt(100);
            maxHeap.add(arr[i]); 
            queue.enqueue(arr[i]);
        }

        System.out.println("通过 add 方式创建的最大堆");
        validate(maxHeap);
        
        // 通过 heapify 创建的最大堆
        MaxHeap<Integer> maxHeap2 = new MaxHeap<Integer>(arr); // 通过 heapify 的方式创建的最大堆
        System.out.println("通过 heapify 创建的最大堆");
        validate(maxHeap2);
        
        // 测试优先队列
        System.out.println("优先队列");
        validate(queue);
    }

    private static void validate(MaxHeap<Integer> maxHeap) {
        // 不断取出堆顶元素，直到堆为空。取出的元素顺序应该为降序
        int prev = maxHeap.extractMax();
//        System.out.println(prev);
        while (!maxHeap.isEmpty()) {
            int cur = maxHeap.extractMax();
            if (prev < cur) {
                throw new IllegalStateException("堆构建错误！");
            }
            prev = cur;
//            System.out.println(prev);
        }

        System.out.println("堆构建正确！");
    }
    
    private static void validate(PriorityQueue<Integer> queue) {
        // 不断取出堆顶元素，直到堆为空。取出的元素顺序应该为降序
        int prev = queue.dequeue();
//        System.out.println(prev);
        while (!queue.isEmpty()) {
            int cur = queue.dequeue();
            if (prev < cur) {
                throw new IllegalStateException("优先队列构建错误！");
            }
            prev = cur;
//            System.out.println(prev);
        }

        System.out.println("优先队列构建正确！");
    }
}
