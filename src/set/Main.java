package set;

public class Main {
    
    public static void main(String[] args) {
        Set<Integer> bstSet = new BSTSet<Integer>();
        bstSet.add(1);
        bstSet.add(2);
        bstSet.add(2);
        bstSet.add(5);
        
        System.out.println("set size: " + bstSet.size());
    }
}
