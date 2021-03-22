package segmenttree;

public class Main {

    public static void main(String[] args) {
        Integer[] arr = { 0, 1, 2, 3, 4, 5 };

        SegmentTree<Integer> segmentTree = new SegmentTree<Integer>(arr, (a, b) -> a + b);
        segmentTree.update(1, 10);
        System.out.println(segmentTree.query(1, 4));
    }
}
