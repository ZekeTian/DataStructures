package stack;

public class Main {
    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<Integer>();

        System.out.println(stack); // bottom[ ]top

        System.out.println("isEmpty: " + stack.isEmpty()); // true
        stack.push(10);
        stack.push(20);
        stack.push(30);
        stack.push(40);
        System.out.println("isEmpty: " + stack.isEmpty()); // false
        System.out.println("size: " + stack.size()); // 4
        System.out.println("stack: " + stack); // bottom[ 10 20 30 40 ]top

        System.out.println("pop: " + stack.pop()); // 40
        System.out.println("peek: " + stack.peek()); // 30
        System.out.println("size: " + stack.size()); // 3
        System.out.println("stack: " + stack); // bottom[ 10 20 30 ]top

        System.out.println("pop: " + stack.pop()); // 30
        System.out.println("stack: " + stack); // bottom[ 10 20 ]top

    }
}
