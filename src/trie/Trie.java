package trie;

import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

/**
 * 字典树（也称前缀树），是一种树形数据结构，可用于检索字符串数据中的 Key。其有非常多的应用，如自动补全、拼写检查。
 * 
 */
public class Trie {

    /**
     * Trie 的节点类
     */
    private class Node {
        /**
         * 标记当前节点是否是一个单词的结尾字符。如果是，则为 true，表明能构成一个单词；否则，为 false
         */
        boolean isWord;
        /**
         * 下一层的多叉子节点，Key：各个子节点所代表的字符，Value：子节点的位置
         */
        TreeMap<Character, Node> next;

        public Node() {
            this(false);
        }

        public Node(boolean isWord) {
            this.isWord = isWord;
            next = new TreeMap<Character, Node>();
        }
    }

    /**
     * 根节点
     */
    private Node root;

    /**
     * Trie 中存储的单词个数
     */
    private int size;

    public Trie() {
        root = new Node();
        size = 0;
    }

    /**
     * 向 Trie 中添加一个单词
     * @param word 待添加的单词
     */
    public void add(String word) {
        // 遍历 word 中的字符，从根节点出发，根据 word 中的字符选择子节点方向，然后向下继续寻找
        Node current = root; // Trie 中当前所处的节点位置
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (current.next.containsKey(c)) {
                current = current.next.get(c);
            } else {
                // 如果未找到字符对应的子节点，则创建该子节点
                Node child = new Node();
                current.next.put(c, child);
                current = child;
            }
        }

        current.isWord = true;
        ++size;
    }

    /**
     * 判断 Trie 中是否含有单词  word
     * @param word 待判断的单词
     * @return 如果 Trie 中含有单词 word，则返回 true；否则，返回 false
     */
    public boolean contains(String word) {
        // 遍历 word 中的字符，从根节点出发，根据 word 中的字符选择子节点方向，然后向下继续寻找
        Node current = root; // Trie 中当前所处的节点位置
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);

            if (current.next.containsKey(c)) {
                // 如果根据字符能够找到对应的子节点，则继续向下寻找
                current = current.next.get(c);
            } else {
                // 如果根据字符未能找到对应的子节点，则说明 Trie 中不包含该单词，直接返回 false
                return false;
            }
        }

        // 需要注意的是，即使没有提前 return 结束，能够正常遍历完 word 中的字符，但是也不能直接返回 true。
        // 因为 Trie 中可能只是包含这个单词所对应的前缀，并未真正包含该单词。如：compute，computer，Trie 中包含 computer，但是不包含 compute
        // 因此，还需要判断最后所处的节点是否构成了一个单词。 如果构成，则说明 Trie 中包含单词 word；否则，不包含。
        return current.isWord;
    }

    /**
     * 在 Trie 中匹配字符串 word，如果能匹配到，则返回 true；否则，返回 false。
     * @param word 待匹配的字符串，其可能包含 “.”， “.” 表示可以匹配任意字母
     * @return 如果 Trie 中能匹配到 word，则返回 true；否则，返回 false。
     */
    public boolean match(String word) {
        return match(root, word, 0);
    }

    /**
     * 判断 prefix 是否是 Trie 中的前缀
     * @param prefix 待判断的前缀
     * @return 如果 prefix 是 Trie 中的前缀，则返回 true；否则，返回 false
     */
    public boolean isPrefix(String prefix) {
        // 遍历 prefix 中的字符，从根节点出发，根据 prefix 中的字符选择子节点的方向，从而继续向下走
        Node current = root;
        for (int i = 0; i < prefix.length(); ++i) {
            char c = prefix.charAt(i);
            if (current.next.containsKey(c)) {
                current = current.next.get(c);
            } else {
                // 无法找到当前字符 c 对应的子节点，说明 Trie 中不包含该字符，直接 return false
                return false;
            }
        }
        // 逐个遍历 prefix 中的字符，在 Trie 中可以一直走下去，说明 Trie 包含 prefix 中所有字符 
        return true;
    }

    public int size() {
        return size;
    }

    /**
     * 在以 root 为根节点的 Trie 中匹配 word，如果能够匹配到，则返回 true；否则，返回 false
     * @param root  Trie 的根节点
     * @param word  待匹配的字符串
     * @param index word 中正在匹配的字符的索引
     * @return 如果 Trie 能够匹配到 word，则返回 true；否则，返回 false
     */
    private boolean match(Node root, String word, int index) {
        // 当 word 中所有的字符都匹配完，则终止递归。能否匹配到 word 这个单词，取决于匹配到的最后一个节点是否代表一个单词
        if (index == word.length()) {
            return root.isWord;
        }

        // 获取当前正在匹配的字符
        char c = word.charAt(index);
        if ('.' == c) {
            // 如果当前字符是 “.”，则所有子节点都需要进行匹配
            Set<Entry<Character, Node>> childNodes = root.next.entrySet();
            for (Entry<Character, Node> child : childNodes) {
                if (match(child.getValue(), word, index + 1)) {
                    return true; // 只要有一个子节点后面能够匹配完 word 剩余的字符，则说明 Trie 中包含 word，直接返回 true，无需对剩余的子节点进行匹配
                }
            }
            // 所有的子节点都不能继续匹配 word，则以 root 为根节点的 Trie 无法匹配到 word
            return false;
        } else {
            // 如果当前字符是一般字母，则判断能否根据当前字符找到对应的子节点
            // 如果根据当前字符未能找到对应的子节点，则 return false
            if (!root.next.containsKey(c)) {
                return false;
            }

            // 如果能找到对应的子节点，则以该子节点为根节点，继续匹配
            return match(root.next.get(c), word, index + 1);
        }
    }
}
