package array;

public class Main {

    public static void main(String[] args) {
        Array<Integer> array = new Array<Integer>();

        System.out.println("isEmpty: " + array.isEmpty()); // true

        for (int i = 10; i < 20; ++i) {
            array.addLast(i);
        }

        System.out.println("size: " + array.size()); // 10
        System.out.println("isEmpty: " + array.isEmpty()); // false
        System.out.println("array: " + array); // [ 10 11 12 13 14 15 16 17 18 19 ]

        for (int i = 0; i < 5; ++i) {
            array.addFirst(i);
        }

        System.out.println("after addFirst");
        System.out.println("array: " + array); // [ 4 3 2 1 0 10 11 12 13 14 15 16 17 18 19 ]

        array.add(5, -1);
        System.out.println("add(5, -1): " + array); // [ 4 3 2 1 0 -1 10 11 12 13 14 15 16 17 18 19 ]

        array.set(5, 0);
        System.out.println("set(5, 0): " + array); // [ 4 3 2 1 0 0 10 11 12 13 14 15 16 17 18 19 ]

        System.out.println("contains(10): " + array.contains(10)); // true
        array.removeElement(10);
        System.out.println("after removeElement(10):" + array); // [ 4 3 2 1 0 0 11 12 13 14 15 16 17 18 19 ]
        System.out.println("contains(10): " + array.contains(10)); // false

        for (int i = 0; i < 5; ++i) {
            array.remove(i);
        }

        // 4 3 2 1 0 0 11 12 13 14 15 16 17 18 19
        // remove(0): 3 2 1 0 0 11 12 13 14 15 16 17 18 19
        // remove(1): 3 1 0 0 11 12 13 14 15 16 17 18 19
        // remove(2): 3 1 0 11 12 13 14 15 16 17 18 19
        // remove(3): 3 1 0 12 13 14 15 16 17 18 19
        // remove(4): 3 1 0 12 14 15 16 17 18 19

        System.out.println("after remove: " + array); // [ 3 1 0 12 14 15 16 17 18 19 ]

        System.out.println("removeFirst: " + array.removeFirst()); // 3
        System.out.println("removeLast: " + array.removeLast()); // 19

        System.out.println("after remove: " + array); // [ 1 0 12 14 15 16 17 18 ]

        // 测试缩容的 resize
        int shrinkNum = (array.getCapacity() / 4) - 1; // 缩容的阈值
        while (array.size() > shrinkNum) {
            array.removeLast();
        }

        System.out.println("after shrink: " + array); // [ 1 0 12 14 ]
    }
}
