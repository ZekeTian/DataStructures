package unionfind;

/**
 * 并查集，可高效地解决连接问题
 * 只进行查询操作，不进行更新操作。
 */
public interface UnionFind {
    
    /**
     * 合并 p、q 所属的集合
     * @param p
     * @param q
     */
    public void union(int p, int q);
    
    /**
     * 判断 p、q 是否属于同一个集合 
     * @param p
     * @param q
     * @return
     */
    public boolean isConnected(int p, int q);
    
    public int size();
}
