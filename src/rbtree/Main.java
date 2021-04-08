package rbtree;

public class Main {

    public static void main(String[] args) {
        RBTree<Integer, String> rbTree = new RBTree<Integer, String>();
        
        rbTree.put(10, "10");
        rbTree.put(30, "30");
        rbTree.put(20, "20");
        rbTree.put(50, "50");
        rbTree.put(40, "40");
        
        System.out.println("isBST: " + rbTree.isBST());
        System.out.println("size: " + rbTree.size());
        System.out.println("contains(10): " + rbTree.contains(10));
        System.out.println("contains(100): " + rbTree.contains(100));

        System.out.println();
        System.out.println("remove result: " + rbTree.remove(30));
        System.out.println("after remove(30)");
        System.out.println("isBST: " + rbTree.isBST());
        System.out.println("size: " + rbTree.size());
        System.out.println("contains(10): " + rbTree.contains(10));
        System.out.println("contains(30): " + rbTree.contains(30));
    }
}
