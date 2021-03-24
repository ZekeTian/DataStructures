package trie;

public class Main {

    public static void main(String[] args) {
        Trie trie = new Trie();
        //        trie.add("computer");
        //        trie.add("compare");
        //        trie.add("class");
        //        
        //        System.out.println("size: " + trie.size());
        //        System.out.println("contains computer: " + trie.contains("computer")); // true
        //        System.out.println("contains class: " + trie.contains("class")); // true
        //        System.out.println("contains compute: " + trie.contains("compute")); // false
        //        System.out.println("contains science: " + trie.contains("science")); // false
        //        
        //        System.out.println("prefix compute: " + trie.isPrefix("compute")); // true
        //        System.out.println("prefix sci: " + trie.isPrefix("sci")); // false
        
        trie.add("bad");
        trie.add("dad");
        trie.add("mad");
        System.out.println(trie.match("pad")); // false
        System.out.println(trie.match("bad")); // true
        System.out.println(trie.match(".ad")); // true
        System.out.println(trie.match(".g.")); // false
        System.out.println(trie.match("b..")); // true
        System.out.println(trie.match("ba")); // false
    }
}
