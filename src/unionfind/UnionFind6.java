package unionfind;

/**
 * 并查集 v6，基于树实现（路径压缩，让树的高度始终维持成 2）
 * 在树中，子节点指向父节点。并且，树的根顶点代表这棵树对应的元素集合的 id。
 */
public class UnionFind6 implements UnionFind {

    /**
     * 存储各个元素的父节点 id 
     */
    private int[] parentId;
    /**
     * 不存储每个集合对应树的实际高度，只是存储树的一个 rank 值（大概的估计值），用于元素的合并
     */
    private int[] rank;

    public UnionFind6(int[] data) {
        parentId = new int[data.length];
        rank = new int[data.length];

        // 初始时，每个元素所属的集合都不一样
        for (int i = 0; i < data.length; ++i) {
            parentId[i] = i;
            rank[i] = 1;
        }
    }

    @Override
    public void union(int p, int q) {
        // 因为 p、q 所属的集合由 p、q 所属的树的根顶点决定，因此，只要合并 p、q 所属的树的根顶点即可。
        int pId = find(p);
        int qId = find(q);
        if (pId == qId) {
            return;
        }

        // 根据树的高度进行合并（将低的树合并到高的树里面）
        if (rank[pId] == rank[qId]) {
            parentId[qId] = pId; // 将 q 所属的树的根顶点指向 p所属的树的根顶点，从而连接两棵树
            ++rank[pId]; // 高度相等的两棵树合并时，高度需要加 1
        } else if (rank[pId] > rank[qId]) {
            parentId[qId] = pId;
        } else { // rank[pId] < rank[qId]
            parentId[pId] = qId; 
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int size() {
        return parentId.length;
    }

    /**
     * 判断 index 处的元素是否为根顶点
     * @param index 索引
     * @return 如果 index 处的元素是根顶点，则返回 true；否则，返回 false。
     */
    private boolean isRoot(int index) {
        return parentId[index] == index; // 根顶点所存储的父节点 id 与其索引值一样
    }

    /**
     * 寻找 index 处的元素所属的集合 id，实际上也就是树的根顶点
     * @param index 索引
     * @return 返回 index 处元素的集合 id
     */
    private int find(int index) {
        if (index < 0 || index >= parentId.length) {
            throw new IllegalArgumentException("Illegal index!");
        }

        if (isRoot(index)) {
            return index;
        }
        
        // 不是根顶点，则寻找 index 的根顶点，并且让当前节点指向找到的根顶点
        parentId[index] = find(parentId[index]);

        return parentId[index]; // 返回的即为根顶点
    }
}
