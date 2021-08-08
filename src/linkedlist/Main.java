package linkedlist;

public class Main {

    public static void main(String[] args) {
        //        LinkedList<Integer> list = new LinkedList<Integer>();
        DoubleLinkedList<Integer> list = new DoubleLinkedList<Integer>();

        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4);

        System.out.println(list); // head->1->2->3->4

        System.out.println("add(2, 3)");
        list.add(2, -3);
        System.out.println(list); // head->1->2-> -3 ->3->4

        System.out.println("add(0, 0)");
        list.add(0, 0);
        System.out.println(list); // head->0->1->2-> -3 ->3->4

        System.out.println("add(linkedList.size() - 1, 100)");
        list.add(list.size() - 1, 100);
        System.out.println(list); // head->0->1->2-> -3 ->3->100->4

        System.out.println("add(200)");
        list.add(200);
        System.out.println(list); // head->0->1->2-> -3 ->3->100->4->200

        System.out.println("set(2, 20)");
        list.set(2, 20);
        System.out.println(list); // head->0->1->20-> -3 ->3->100->4->200

        System.out.println("set(0, 200)");
        list.set(0, 200);
        System.out.println(list); // head->200->1->20-> -3 ->3->100->4->200

        System.out.println("set(linkedList.size() - 1, -200)");
        list.set(list.size() - 1, -200);
        System.out.println(list); // head->200->1->20-> -3 ->3->100->4->-200

        System.out.println("get(0)");
        System.out.println(list.get(0)); // 200

        System.out.println("get(linkedList.size() - 1)");
        System.out.println(list.get(list.size() - 1)); // -200

        System.out.println("get(2)");
        System.out.println(list.get(2)); // 20

        System.out.println("remove(0)");
        System.out.println(list.remove(0)); // 200
        System.out.println(list); // head->1->20-> -3 ->3->100->4->-200

        System.out.println("remove(linkedList.size() - 1)");
        System.out.println(list.remove(list.size() - 1)); // -200
        System.out.println(list); // head->1->20-> -3 ->3->100->4

        System.out.println("remove(2)");
        System.out.println(list.remove(2)); // -3
        System.out.println(list); // head->1->20->3->100->4

        System.out.println("size()");
        System.out.println(list.size()); // 5
    }
}
