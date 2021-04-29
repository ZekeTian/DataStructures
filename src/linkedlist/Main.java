package linkedlist;

public class Main {

    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<Integer>();
        
        linkedList.add(1);
        linkedList.add(2);
        linkedList.add(3);
        linkedList.add(4);
        
        System.out.println(linkedList); // head->1->2->3->4
        
        System.out.println("add(2, 3)");
        linkedList.add(2, -3);
        System.out.println(linkedList); // head->1->2-> -3 ->3->4
        
        
        System.out.println("add(0, 0)");
        linkedList.add(0, 0);
        System.out.println(linkedList); // head->0->1->2-> -3 ->3->4
        
        System.out.println("add(linkedList.size() - 1, 100)");
        linkedList.add(linkedList.size() - 1, 100);
        System.out.println(linkedList); // head->0->1->2-> -3 ->3->100->4
        
        System.out.println("add(200)");
        linkedList.add(200);
        System.out.println(linkedList); // head->0->1->2-> -3 ->3->100->4->200
        
        System.out.println("set(2, 20)");
        linkedList.set(2, 20);
        System.out.println(linkedList); // head->0->1->20-> -3 ->3->100->4->200
        
        
        System.out.println("set(0, 200)");
        linkedList.set(0, 200);
        System.out.println(linkedList); // head->200->1->20-> -3 ->3->100->4->200
        
        System.out.println("set(linkedList.size() - 1, -200)");
        linkedList.set(linkedList.size() - 1, -200);
        System.out.println(linkedList); // head->200->1->20-> -3 ->3->100->4->-200
        
        System.out.println("get(0)");
        System.out.println(linkedList.get(0)); // 200

        System.out.println("get(linkedList.size() - 1)");
        System.out.println(linkedList.get(linkedList.size() - 1)); // -200
        
        System.out.println("get(2)");
        System.out.println(linkedList.get(2)); // 20
        
        
        System.out.println("remove(0)");
        System.out.println(linkedList.remove(0)); // 200
        System.out.println(linkedList); // head->1->20-> -3 ->3->100->4->-200

        System.out.println("remove(linkedList.size() - 1)");
        System.out.println(linkedList.remove(linkedList.size() - 1)); // -200
        System.out.println(linkedList); // head->1->20-> -3 ->3->100->4
        
        System.out.println("remove(2)");
        System.out.println(linkedList.remove(2)); // -3
        System.out.println(linkedList); // head->1->20->3->100->4
        
        System.out.println("size()");
        System.out.println(linkedList.size()); // 5
    }
}
