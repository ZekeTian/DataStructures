package rbtree;

public class Main {

    public static void main(String[] args) {
        RBTree<Integer, String> rbTree = new RBTree<Integer, String>();

        //        rbTree.put(10, "10");
        //        rbTree.put(30, "30");
        //        rbTree.put(20, "20");
        //        rbTree.put(50, "50");
        //        rbTree.put(40, "40");
        //
        //        System.out.println("isBST: " + rbTree.isBST()); // true
        //        System.out.println("size: " + rbTree.size()); // 5
        //        System.out.println("contains(10): " + rbTree.contains(10)); // true
        //        System.out.println("contains(100): " + rbTree.contains(100)); // false
        //
        //        System.out.println();
        //        System.out.println("remove result: " + rbTree.remove(30)); // remove 30
        //        System.out.println("after remove(30)");
        //        System.out.println("isBST: " + rbTree.isBST()); // true
        //        System.out.println("size: " + rbTree.size()); // 4
        //        System.out.println("contains(10): " + rbTree.contains(10)); // true
        //        System.out.println("contains(30): " + rbTree.contains(30)); // false

        // 三个红节点(17、42、66)
        rbTree.put(42, "42");
        rbTree.put(17, "17");
        rbTree.put(33, "33");
        rbTree.put(50, "50");
        rbTree.put(6, "6");
        rbTree.put(12, "12");
        rbTree.put(18, "18");
        rbTree.put(37, "37");
        rbTree.put(48, "48");
        rbTree.put(66, "66");
        rbTree.put(88, "88");

        // 全部为黑色
        //        rbTree.put(42, "42");
        //        rbTree.put(37, "37");
        //        rbTree.put(12, "12");
        //        rbTree.put(18, "18");
        //        rbTree.put(6, "6");
        //        rbTree.put(11, "11");
        //        rbTree.put(5, "5");

        System.out.println("isBST: " + rbTree.isBST());
        rbTree.print();
    }
}
