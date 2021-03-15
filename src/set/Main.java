package set;

public class Main {

    public static void main(String[] args) {
//        Set<Integer> bstSet = new BSTSet<Integer>();
        Set<Integer> set = new LinkedListSet<Integer>();
        set.add(1);
        set.add(2);
        set.add(2);
        set.add(5);

        System.out.println("set size: " + set.size());
    }
}
