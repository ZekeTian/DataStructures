package unionfind;

/**
 * 并查集 v1，基于数组实现
 */
public class UnionFind1 implements UnionFind {

    /**
     * 存储各个元素所属集合的 id，如 0 号元素所属集合是 1 ，1 号元素所属集合是 0，则 id 数组内容：[1, 0]
     */
    private int[] id;

    public UnionFind1(int[] data) {
        id = new int[data.length];

        // 如果没有指定元素所属集合，则默认所有元素所属的集合都不一样
        for (int i = 0; i < data.length; ++i) {
            id[i] = i;
        }
    }

    @Override
    public void union(int p, int q) {
        if (isConnected(p, q)) {
            return;
        }
        
        // p、q 属于不同集合，则进行合并
        int pId = find(p); // p 所属的集合 id
        int qId = find(q); // q 所属的集合 id
        // 找到属于集合 qId 的所有元素，然后将其放入 pId 集合 
        for (int i = 0; i < id.length; ++i) {
            if (id[i] == qId) {
                id[i] = pId;
            }
        }
    }

    @Override
    public boolean isConnected(int p, int q) {
        return find(p) == find(q);
    }

    @Override
    public int size() {
        return id.length;
    }

    /**
     * 寻找 index 处的元素所属集合
     * @param index 索引
     * @return index 处元素所属集合的 id
     */
    private int find(int index) {
        if (index < 0 || index >= id.length) {
            throw new IllegalArgumentException("Illegal index!");
        }
        return id[index];
    }

}
