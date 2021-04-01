package unionfind;

public class Main {

    public static void main(String[] args) {
        int[] data = {0, 1, 2, 3, 4, 5};
//        UnionFind unionFind = new UnionFind1(data);
//        UnionFind unionFind = new UnionFind2(data);
//        UnionFind unionFind = new UnionFind3(data);
//        UnionFind unionFind = new UnionFind4(data);
//        UnionFind unionFind = new UnionFind5(data);
        UnionFind unionFind = new UnionFind6(data);
        
        System.out.println(unionFind.getClass());
        
        System.out.println("0 2 isConnected: " + unionFind.isConnected(0, 2)); // false
        System.out.println("4 5 isConnected: " + unionFind.isConnected(4, 5)); // false
        
        // 将 0, 1, 2 合并成一个，3, 4, 5 合并一个
        unionFind.union(0, 1);
        unionFind.union(1, 2);
        System.out.println("0 2 isConnected: " + unionFind.isConnected(0, 2)); // true
        
        unionFind.union(3, 4);
        unionFind.union(3, 5);
        System.out.println("4 5 isConnected: " + unionFind.isConnected(4, 5)); // true
        System.out.println("0 5 isConnected: " + unionFind.isConnected(0, 5)); // false
    }
}
