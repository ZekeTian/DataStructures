package heap;

import java.util.ArrayList;
import java.util.List;

/**
 * 最大堆
 * 
 * 二叉堆是一棵完全二叉树。
 * 
 * 完全二叉树的特点：
 *  （1）完全二叉树不一定是满二叉树，但是其不满的部分一定在右下侧
 *  （2）把元素顺序排列成树的形状
 * 最大二叉堆的特点：堆中某个节点的值总是不大于其父节点的值（或者说父节点的值总是大于等于子节点的值）
 * 
 * 两个较难的操作是：add 和 extracMax。这两个操作都会改变堆的形状，为了尽可能维持堆的树形形状，使用了 siftUp（上浮）和 siftDown（下沉）操作。
 *  siftUp 操作：在添加元素时，将新元素添加到堆数组的最后面，然后将该元素慢慢上浮到合适的位置。
 *  siftDown 操作：在提取最大值时，将最大值与堆数组的最后一个元素交换，然后再将新堆顶点元素慢慢下沉到合适的位置。
 */
public class MaxHeap<E extends Comparable<E>> {
    private List<E> data;

    public MaxHeap() {
        data = new ArrayList<E>();
    }

    public MaxHeap(E[] dataArr) {
        heapify(dataArr);
    }

    public MaxHeap(int capacity) {
        data = new ArrayList<E>(capacity);
    }

    public int size() {
        return data.size();
    }

    public boolean isEmpty() {
        return data.isEmpty();
    }

    /**
     * 实现方式1
     * 向堆中添加元素 e
     * @param e
     */
    public void add(E e) {
        data.add(e);
        if (1 == size()) {
            return;
        }
        siftUp(size() - 1);
    }

    /**
     * 实现方式2
     * 向堆中添加元素 e
     * @param e
     */
    public void add2(E e) {
        data.add(e);
        if (1 == size()) {
            return;
        }

        int currentIndex = data.size() - 1; // 新添加的元素 e 在堆数组中的当前位置
        int parentIndex = parent(currentIndex);

        while (currentIndex > 0 && data.get(parentIndex).compareTo(e) < 0) {
            siftUp2(currentIndex);
            currentIndex = parentIndex;
            parentIndex = parent(currentIndex);
        }
    }

    /**
     * 获取堆中最大的元素（不删除该元素）
     * @return
     */
    public E findMax() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Can not find max in a empty heap!");
        }

        return data.get(0);
    }

    /**
     * 实现方式一
     * 从堆中提取出最大元素（会删除该元素）
     * @return
     */
    public E extractMax() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Can not extract max in a empty heap!");
        }

        E max = findMax();
        if (size() == 1) {
            data.remove(0);
            return max;
        }

        // 将堆顶元素与堆中的最后一个元素交换，从而便于删除最大值
        swap(0, size() - 1);
        data.remove(size() - 1);

        // 将新的堆顶元素下沉到合适位置
        siftDown(0);

        return max;
    }

    /**
     * 实现方式二
     * 从堆中提取出最大元素（会删除该元素）
     * @return
     */
    public E extractMax2() {
        if (isEmpty()) {
            throw new IllegalArgumentException("Can not extract max in a empty heap!");
        }

        E max = findMax();
        if (size() == 1) {
            data.remove(0);
            return max;
        }

        // 将最大值与堆数组中的最后一个元素交换，然后再删除最大值
        swap(0, data.size() - 1);
        data.remove(data.size() - 1); // 删除最大值

        // 将现在的堆顶元素 e 下沉到合适位置
        // 获取元素 e 的左右子节点的最大值 maxChild
        int currentIndex = 0; // 元素 e 在堆中的位置
        // 判断 k 节点是否可以继续下沉。下沉的条件：k 节点有子节点，且 k 节点比最大子节点的值要小
        while (leftChild(currentIndex) < size() /* 保证 k 节点至少有一个子节点 */
                && currentIndex >= 0 /* k 下沉成功 */) {
            // 将 k 节点与子节点中的最大值 maxChild 进行比较，若 k 小于 maxChild 则进行交换，否则停止下沉操作
            currentIndex = siftDown2(currentIndex);
        }

        return max;
    }

    /**
     * 取出最大元素后，放入一个新元素
     * @param e 新元素
     * @return 最大元素
     */
    public E replace(E e) {
        // 第一种实现方式：先 extracMax，然后再 add
        // 第二种实现方式：将元素与最大元素进行交换，然后再对新元素进行 siftDown 操作
        E max = findMax();
        data.set(0, e);
        siftDown(0);

        return max;
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

    /**
     * 上浮操作实现方式一（上浮多次）
     * 将下标为 k 的节点上浮到合适的位置
     * @param k 下标 
     */
    private void siftUp(int k) {
        int parentIndex = parent(k);
        while (k > 0 && data.get(parentIndex).compareTo(data.get(k)) < 0) {
            swap(parentIndex, k); // 父节点小于 k 节点时，需要将 k 节点与父节点交换，从而实现 k 节点的上浮
            k = parentIndex;
            parentIndex = parent(k);
        }
    }

    /**
     * 上浮操作实现方式二（只上浮一次）
     * 将下标为 k 的节点上浮一次
     * @param k 下标 
     */
    private void siftUp2(int k) {
        if (k <= 0 || k >= data.size()) {
            throw new IllegalArgumentException("Index is illegal!");
        }

        // 将下标为 k 的节点与其父节点进行比较
        int parent = parent(k);
        if (data.get(parent).compareTo(data.get(k)) < 0) {
            swap(parent, k);
        }
    }

    /**
     * 下沉操作实现方式一（下沉多次）
     * 将下标为 k 的节点下沉到合适的位置
     * @param k 下标 
     */
    private void siftDown(int k) {
        int leftChildIndex = leftChild(k);
        int rightChildIndex = rightChild(k);
        int maxChildIndex = -1;

        // 确保至少有一个子节点，从而才有可能继续下沉操作
        while (leftChildIndex < size()) {
            // 获取最大的子节点
            E maxChild = data.get(leftChildIndex);
            maxChildIndex = leftChildIndex;
            if (rightChildIndex < size() && maxChild.compareTo(data.get(rightChildIndex)) < 0) {
                maxChild = data.get(rightChildIndex);
                maxChildIndex = rightChildIndex;
            }

            // 判断最大的子节点是否大于 k 节点，如果小于，则不需要下沉
            if (maxChild.compareTo(data.get(k)) <= 0) {
                return; // 已经下沉到合适的位置，不需要继续下沉
            }
            swap(maxChildIndex, k);
            k = maxChildIndex;
            leftChildIndex = leftChild(k);
            rightChildIndex = rightChild(k);
        }
    }

    /**
     * 下沉操作实现方式二（只下沉一次）
     * 将下标为 k 的节点下沉一次，并返回 k 节点下沉后的下标
     * @param k 下标 
     * @return k 节点下沉后的下标。如果 k 节点可以继续下沉，则返回下标；否则，返回 -1.
     */
    private int siftDown2(int k) {
        // 获取 k 节点的左右子节点的最大值 maxChild
        // 将 k 节点与子节点中的最大值 maxChild 进行比较，若 k 小于 maxChild 则进行交换，否则停止下沉操作
        int leftChildIndex = leftChild(k);
        int rightChildIndex = rightChild(k);
        int maxChildIndex = leftChildIndex;

        E maxChild = data.get(maxChildIndex);
        if (rightChildIndex < size()) {
            E rightChild = data.get(rightChildIndex);
            if (maxChild.compareTo(rightChild) < 0) {
                maxChild = rightChild;
                maxChildIndex = rightChildIndex;
            }
        }

        // 判断 maxChild 与 k 节点的值，如果 maxChild 大于 k 节点，则两个节点需要进行交换，k 进行下沉
        if (maxChild.compareTo(data.get(k)) > 0) {
            swap(maxChildIndex, k);
            return maxChildIndex;
        }

        return -1; // k 节点不需要继续下沉
    }

    /**
     * 交换堆数组中下标 index1、index2 两个元素的值
     */
    private void swap(int index1, int index2) {
        E e1 = data.get(index1);
        data.set(index1, data.get(index2));
        data.set(index2, e1);
    }

    /**
     * 将任意数组转换成堆
     */
    private void heapify(E[] dataArr) {
        // data = Arrays.asList(dataArr); // 这种方式得到的 ArrayList 是 Arrays 里面的内部类，而非 java.util 里面的，所以这种方式得到的 List 不能正常使用
        data = new ArrayList<E>(dataArr.length);
        for (E e : dataArr) {
            data.add(e);
        }
        
        // 实现方式一：遍历数组的元素，然后逐个添加到堆中，时间复杂度：O(nlogn)
        // 实现方式二：将数组视作一个完全二叉树，然后从最后一个非叶子节点开始，从后向前进行 siftDown 操作（即从最后一个非叶子节点到二叉树中的根顶点）
        // 从最后一个非叶子节点开始进行 siftDown 操作，在这个过程会对每个叶子节点进行比较，从而可以使得较大的叶子顶点升上去。然后再对上一层的顶点进行
        // siftDown 操作时，又会使得下面较大的节点升上去。即对第 i 层节点进行下沉操作时，会让第 i 层下面的较大的顶点升上去，然后在对第 i-1 层节点
        // 进行下沉操作时，又会使得第 i 层以及下面的较大的节点升上去。
        // 方式二中的一个关键操作是确定最后一个非叶子节点，而最后一个非叶子节点实际就是最后一个节点的父节点
        for (int i = parent(data.size() - 1); i >= 0; --i) {
            siftDown(i);
        }
    }
}
