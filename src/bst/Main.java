package bst;

public class Main {

    public static void main(String[] args) {
        //
        //     5
        //    / \
        //   3   6
        //  / \   \
        // 2   4   8 
        //
        BST<Integer> bst = new BST<Integer>();
        int[] nums = { 5, 3, 5, 6, 8, 4, 2 };

        for (int n : nums) {
            bst.add(n);
            System.out.println("size: " + bst.size());
        }

        //        System.out.println("层序：");
        //        bst.levelOrder();

        //        System.out.println("size: " + bst.size());
        //
        //        System.out.println("max: " + bst.max());
        //        System.out.println("min: " + bst.min());
        //        System.out.println("contains: " + bst.contains(40));

        //        System.out.println("先序遍历");
        //        bst.preOrder();

        //        System.out.println("递归先序：");
        //        bst.preOrder();
        //        System.out.println("非递归先序：");
        //        bst.preOrderNR();

        //        System.out.println("删除前，中序遍历");
        //        bst.inOrder();
        //        System.out.println("删除的最大值：" + bst.removeMax());
        //        System.out.println("删除的最小值：" + bst.removeMin());
        //        
        //        System.out.println("删除后，中序遍历");
        //        bst.inOrder();

        bst.remove(6);
        System.out.println("删除后，size: " + bst.size());
        bst.remove(80);
        System.out.println("删除后，size: " + bst.size());
        bst.remove(5);
        System.out.println("删除后，size: " + bst.size());
        bst.inOrder();

        //        System.out.println("后序遍历");
        //        bst.postOrder();

    }

}
