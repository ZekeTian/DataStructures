package avl;

import map.Map;

public class Main {

    public static void main(String[] args) {
      AVLTree<Integer, String> map = new AVLTree<Integer, String>();
      map.put(5, "5");
      map.put(3, "3");
      map.put(6, "6");
      map.put(2, "2");
      map.put(4, "4");
      map.put(8, "8");
      map.put(6, "6");
      System.out.println("size: " + map.size());
      System.out.println(map.get(3));
      map.remove(3);
      System.out.println("size: " + map.size());
      System.out.println(map.get(3));
      
      System.out.println("isBST: " + map.isBST());
    }
}
