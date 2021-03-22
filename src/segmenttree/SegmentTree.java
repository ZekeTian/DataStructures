package segmenttree;

/**
 * 线段树的特点：
 *  （1）可以高效地“查询”和“更新”一个“区间”的值
 *  （2）非空叶子节点存储单个元素的值，非叶子节点存储区间内的值（区间内的值可能是这个区间的数字和、最大值、最小值）
 *  （3）不是完全二叉树，但是是平衡二叉树
 * 其解决的问题主要是：
 *   对于给定区间，（1）更新区间中一个元素或者一个区间的值    （2）查询一个区间 [i,j] 的最大值，最小值，或者区间数字和
 *   注意：区间的长度是固定的，即待查询的元素个数是固定的，只是元素的值可能会发生变化
 */
public class SegmentTree<E> {
    /**
     * 按照满二叉树的形式存储数据，非叶子节点存储区间值，非空叶子节点存储原始元素值
     * 满二叉树是一种特殊的完全二叉树，所以可以按照数组存储。但是为了能够存储所有的元素， tree 数组的长度 = 4 * data 数组的长度
     * 在满二叉树中，第 h 层的节点个数 = 前 h-1 层所有节点的个数之和
     */
    private E[] tree; // 线段树数组（按照线段树的形式存储）

    /**
     * 普通形式的存储数据，存储原始的元素值
     */
    private E[] data; // 数据数组

    private Merger<E> merger;

    @SuppressWarnings("unchecked")
    public SegmentTree(E[] arr, Merger<E> merger) {
        data = (E[]) new Object[arr.length];
        this.merger = merger;
        for (int i = 0; i < arr.length; ++i) {
            data[i] = arr[i];
        }

        tree = (E[]) new Object[4 * arr.length];

        buildSegmentTree(0, 0, arr.length - 1);
    }

    /**
     * 在线段树中查询区间 [queryLeft, queryRight] 之间的值
     * @param queryLeft  区间的左边界值 （data 的下标）
     * @param queryRight 区间的右边界值  （data 的下标）
     * @return 线段树中 [queryLeft, queryRight] 之间的值
     */
    public E query(int queryLeft, int queryRight) {
        if (queryLeft < 0 || queryRight >= data.length || queryLeft > queryRight) {
            throw new IllegalArgumentException("Illegal query range!");
        }

        return query(0, 0, data.length - 1, queryLeft, queryRight);
    }

    /**
     * 更新 index 指定索引处的元素
     * @param index 待更新元素的索引
     * @param e     元素的新值
     */
    public void update(int index, E e) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Illegal index");
        }

        // 更新数组数组指定索引处的元素
        data[index] = e;

        // 更新线段树数组
        update(0, 0, data.length - 1, index, e);
    }

    public int size() {
        return data.length;
    }

    /**
     * 返回 index 指定索引处的元素
     * 
     * @param index 元素的索引
     * @return 指定索引处的元素
     */
    public E get(int index) {
        if (index < 0 || index >= data.length) {
            throw new IllegalArgumentException("Illegal index!");
        }
        return data[index];
    }

    /**
     * 以 root 为根节点，构建一棵区间为 [left, right] 的线段树
     * 
     * @param root  根顶点的索引 （tree 的下标）
     * @param left  区间的左边界值 （data 的下标）
     * @param right 区间的右边界值  （data 的下标）
     */
    private void buildSegmentTree(int root, int left, int right) {
        // 当区间内只有一个元素时，终止递归
        if (left == right) {
            tree[root] = data[left];
            return;
        }

        // 计算区间的中间值，将区间一分为二，然后分别构建左右子树
        int mid = left + (right - left) / 2;
        int leftChildIndex = leftChild(root);
        int rightChildIndex = rightChild(root);
        buildSegmentTree(leftChildIndex, left, mid);
        buildSegmentTree(rightChildIndex, mid + 1, right);

        // 左右子树构建完之后，将左右子树代表的区间内的值合并，从而得到当前节点的值
        tree[root] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
    }

    /**
     * 在以 root 为根节点的线段树中查询 [queryLeft, queryRight] 区间的值
     * @param root          线段树的根节点
     * @param left          root 对应区间的左边边界
     * @param right         root 对应区间的右边界
     * @param queryLeft     查询区间的左边界
     * @param queryRight    查询区间的右边界
     * @return
     */
    private E query(int root, int left, int right, int queryLeft, int queryRight) {
        // 待查询的区间与当前线段树所表示的区间一样，则直接返回当前线段树的值即可
        if (left == queryLeft && right == queryRight) {
            return tree[root];
        }

        // 计算当前线段树区间的中间点，确定查询的方向
        // 查询的方向有三种，分别为：只在左子树中查找，只在右子树中查找，在左右子树中都要查找
        int mid = left + (right - left) / 2;
        int leftChildIndex = leftChild(root);
        int rightChildIndex = rightChild(root);

        if (queryRight <= mid) { // 只在左子树中查找
            return query(leftChildIndex, left, mid, queryLeft, queryRight);
        } else if (queryLeft >= mid + 1) { // 只在右子树中查找
            return query(rightChildIndex, mid + 1, right, queryLeft, queryRight);
        }

        // 在左右子树中都进行查找，需要将查询区间分为左右两部分，分别在左右子树中进行查找
        E leftResult = query(leftChildIndex, left, mid, queryLeft, mid);
        E rightResult = query(rightChildIndex, mid + 1, right, mid + 1, queryRight);

        // 将左右子树的结果合并并返回
        return merger.merge(leftResult, rightResult);
    }

    /**
     * 将线段树中 index 处的元素更新为 e
     * @param root  线段树的根顶点 
     * @param left  root 对应区间的左边边界
     * @param right root 对应区间的右边边界
     * @param index 待更新元素的索引
     * @param e     元素的新值
     */
    private void update(int root, int left, int right, int index, E e) {
        // 当区间中只有一个元素，且该区间对应的索引是 index 时，终止递归。（更新 index 处的元素，相当于更新 [index, index] 区间的元素）
        if (left == right && left == index) {
            tree[root] = e; // 当前线段树的根顶点即为数据数组中 index 处的元素，因此需要将根顶点的元素进行更新
            return;
        }

        // 确定 index 所在的子树（可能在左子树，也可能在右子树），并对该子树进行更新
        int mid = left + (right - left) / 2;
        int leftChildIndex = leftChild(root);
        int rightChildIndex = rightChild(root);
        if (index <= mid) { // 在左子树中
            update(leftChildIndex, left, mid, index, e);
        } else { // 在右子树中
            update(rightChildIndex, mid + 1, right, index, e);
        }

        tree[root] = merger.merge(tree[leftChildIndex], tree[rightChildIndex]);
    }

    /**
     * 获取下标为 i 的节点的父节点
     * @param  i 下标
     * @return i 号节点的父节点
     */
    private int parent(int i) {
        // 下标从 1 开始， parent(i) = i/2; leftChild(i) = 2*i; rightChild(i) = 2*i + 1 
        // 下标从 0 开始， parent(i) = (i-1)/2; leftChild(i) = 2*i + 1; rightChild(i) = 2*i + 2 
        return (i - 1) / 2;
    }

    /**
     * 获取下标为 i 的节点的左子节点
     * @param  i 下标
     * @return i 号节点的左子节点
     */
    private int leftChild(int i) {
        return 2 * i + 1;
    }

    /**
     * 获取下标为 i 的节点的右子节点
     * @param  i 下标
     * @return i 号节点的右子节点
     */
    private int rightChild(int i) {
        return 2 * i + 2;
    }
}
