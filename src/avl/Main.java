package avl;

public class Main {

    public static void main(String[] args) {
        // testAVLTree();
        // testAVLMap();
        testAVLSet();
    }

    private static void testAVLTree() {
        AVLTree<Integer, String> map = new AVLTree<Integer, String>();

        // 插入的数据如果是有序的，则平衡树退化成链表
        // 5                     6
        //  \                   / \
        //   6     平衡处理后               5  7
        //    \    =======>        \
        //     7                    8
        //      \
        //       8
        map.put(5, "5");
        map.put(6, "6");
        map.put(7, "7");
        map.put(8, "8");

        System.out.println("isBST: " + map.isBST());
        System.out.println("isBalance: " + map.isBalance()); // 如果在添加时不进行平衡处理，则为 false

        System.out.println("remove: " + map.remove(5));
        System.out.println("isBST: " + map.isBST());
        System.out.println("isBalance: " + map.isBalance()); // 如果在删除时不进行平衡处理，则为 false

        //     5
        //    / \
        //   3   6
        //  / \   \
        // 2   4   8 
        //
        // { 5, 3, 5, 6, 8, 4, 2 };

        //        map.put(5, "5");
        //        map.put(3, "3");
        //        map.put(5, "5");
        //        map.put(6, "6");
        //        map.put(8, "8");
        //        map.put(4, "4");
        //        map.put(2, "2");
        //        System.out.println("size: " + map.size());

        //        System.out.println(map.get(3));
        //        map.remove(3);
        //        System.out.println("size: " + map.size());
        //        System.out.println(map.get(3));

        //        System.out.println("isBST: " + map.isBST());
        //        System.out.println("isBalance: " + map.isBalance());
    }

    private static void testAVLMap() {
        AVLMap<Integer, String> map = new AVLMap<Integer, String>();

        // 插入的数据如果是有序的，则平衡树退化成链表
        // 5                     6
        //  \                   / \
        //   6     平衡处理后               5  7
        //    \    =======>        \
        //     7                    8
        //      \
        //       8
        map.put(5, "5");
        map.put(6, "6");
        map.put(7, "7");
        map.put(8, "8");

        System.out.println("size: " + map.size());

        System.out.println("get(5): " + map.get(5));
        System.out.println("remove(5)");
        map.remove(5);
        System.out.println("get(5): " + map.get(5));

        System.out.println("after remove, size: " + map.size());
    }

    private static void testAVLSet() {
        AVLSet<Integer> set = new AVLSet<Integer>();

        // 插入的数据如果是有序的，则平衡树退化成链表
        // 5                     6
        //  \                   / \
        //   6     平衡处理后               5  7
        //    \    =======>        \
        //     7                    8
        //      \
        //       8
        set.add(5);
        set.add(6);
        set.add(6);
        set.add(7);
        set.add(8);

        System.out.println("size: " + set.size());

        System.out.println("get(5): " + set.contains(5));
        System.out.println("remove(5)");
        set.remove(5);
        System.out.println("get(5): " + set.contains(5));

        System.out.println("after remove, size: " + set.size());
    }
}
