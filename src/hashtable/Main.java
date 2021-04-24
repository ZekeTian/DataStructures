package hashtable;

public class Main {

    public static void main(String[] args) {

        TreeHashTable<Integer, Integer> hashTable = new TreeHashTable<Integer, Integer>();

        // test isEmpty、put、remove、contains、get、size
        System.out.println("isEmpty: " + hashTable.isEmpty()); // true
        hashTable.put(10, 10);
        hashTable.put(20, 20);
        hashTable.put(30, 30);
        hashTable.put(40, 40);
        hashTable.put(50, 50);

        System.out.println("size: " + hashTable.size()); // 5
        System.out.println("contians(40): " + hashTable.contains(40)); // true
        System.out.println("remove: " + hashTable.remove(40)); // 40
        System.out.println("contians(40): " + hashTable.contains(40)); // false
        System.out.println("size: " + hashTable.size()); // 4

        hashTable.put(20, 200);
        System.out.println("get(20): " + hashTable.get(20)); // 200

        System.out.println("isempty: " + hashTable.isEmpty()); // false

        // test resize （UPPER = 2， LOWER = 1）
        //        for (int i = 0; i < 110; ++i) {
        //            hashTable.put(i, i);
        //        }
        //
        //        for (int i = 0; i < 60; ++i) {
        //            hashTable.remove(i);
        //        }
    }
}
