package segmenttree;

/**
 * 用于将两个元素合并成一个
 */
public interface Merger<E> {

    /**
     * 将元素 e1、e2 合并，并返回
     * @param e1 待合并的元素 1
     * @param e2 待合并的元素 2
     * @return e1、e2 合并后的结果
     */
    public E merge(E e1, E e2);
}
