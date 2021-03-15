package map;

public class Main {
    
    public static void main(String[] args) {
        Map<Integer, String> map = new LinkedListMap<Integer, String>();
        map.put(1, "aaa");
        map.put(2, "bbb");
        map.put(1, "ccc");
        System.out.println(map.get(1));
        System.out.println("size: " + map.size());
        map.remove(1);
        System.out.println(map.get(1));
        System.out.println("size: " + map.size());
    }
}
