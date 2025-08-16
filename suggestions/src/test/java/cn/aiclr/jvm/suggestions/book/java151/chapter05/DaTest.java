package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class DaTest {

    private static final Logger logger = LoggerFactory.getLogger(DaTest.class);

    /**
     * <pre>{@link java.util.ArrayList#contains(Object)} 就是一个遍历对比，
     * for 循环逐个进行遍历，判断 equals 的返回值是否为 true，为 true 即找到结果，不再遍历
     * 源码:
     * <code>
     *      public boolean contains(Object o) {
     *         return indexOf(o) >= 0;
     *     }
     *      public int indexOf(Object o) {
     *         return indexOfRange(o, 0, size);
     *     }
     *     int indexOfRange(Object o, int start, int end) {
     *         Object[] es = elementData;
     *         if (o == null) {
     *             for (int i = start; i < end; i++) {
     *                 if (es[i] == null) {
     *                     return i;
     *                 }
     *             }
     *         } else {
     *             for (int i = start; i < end; i++) {
     *                 if (o.equals(es[i])) {
     *                     return i;
     *                 }
     *             }
     *         }
     *         return -1;
     *     }
     * </code>
     */
    @Test
    void testArrayListContains() {
        int size = 10 * 1000;
        List<String> list = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            list.add("value" + i);
        }
        long start = System.nanoTime();
        list.contains("value" + (size - 1));
        long end = System.nanoTime();
        logger.info("ArrayList.contains 耗时 {} ns", end - start);
    }

    /**
     * <pre> jdk7 {@link java.util.HashMap} 实现的一个关键点，能根据 hashCode 定位它在数组中的位置。
     *
     * {@link java.util.HashMap} 的 {@link java.util.HashMap#table} 数组存储元素
     *      1.table 数组的长度永远是 2 的 N 次幂
     *      2.table 数组中的元素是 {@link java.util.HashMap.Node} 类型
     *      3.table 数组中的元素位置是不连续的
     *
     * {@link java.util.HashMap} 插入元素计算 hashCode，定位元素
     * hashCode 相同，并且 key equals = true ，则覆盖
     * {@link java.util.HashMap} 每次增加元素时都会先计算其 hashCode，
     * 然后使用 hash 方法再次对 hashCode 进行抽取和统计，
     * 同时兼顾 hashCode 的高位和低位信息产生一个唯一值，也就是说 hashCode 不同，hash 方法返回的值也不同。
     * 之后再通过 indexFor 方法与数组长度做一次与运算，即可计算出其在数组中的位置，
     * 简单地说，hash 方法和 indexFor 方法就是把哈希码转变成数组的下标。
     *
     * null 值也是可以作为 key 值的，它的位置永远是在 Node 数组中的第一位
     *
     * 哈希运算存在着哈希冲突问题，
     * 即对于一个固定的哈希算法 f(k)，允许出现 f(k1) = f(k2)，
     * k1 != k2 的情况就是说两个不同的 Node，可能产生相同的哈希码，
     * {@link java.util.HashMap} 是通过链表处理这个问题的，
     * 每个键值对都是一个 Node，
     * 其中每个 Node 都有一个next变量，
     * 也就是说它会指向下一个键值对 --- 很明显，这应该是一个单向链表，该链表是由 addEntry 方法完成的
     *
     * 源码:
     * <code>
     *     public boolean containsKey(Object key) {
     *         return getNode(key) != null;
     *     }
     *      final Node<K,V> getNode(Object key) {
     *         Node<K,V>[] tab; Node<K,V> first, e; int n, hash; K k;
     *         if ((tab = table) != null && (n = tab.length) > 0 &&
     *             (first = tab[(n - 1) & (hash = hash(key))]) != null) {
     *             if (first.hash == hash && // always check first node
     *                 ((k = first.key) == key || (key != null && key.equals(k))))
     *                 return first;
     *             if ((e = first.next) != null) {
     *                 if (first instanceof TreeNode)
     *                     return ((TreeNode<K,V>)first).getTreeNode(hash, key);
     *                 do {
     *                     if (e.hash == hash &&
     *                         ((k = e.key) == key || (key != null && key.equals(k))))
     *                         return e;
     *                 } while ((e = e.next) != null);
     *             }
     *         }
     *         return null;
     *     }
     * </code>
     */
    @Test
    void testHashMapContainKey() {
        int size = 10 * 1000;
        Map<String, String> map = new HashMap<>();
        for (int i = 0; i < size; i++) {
            map.put("key" + i, "value" + i);
        }
        long start = System.nanoTime();
        map.containsKey("key" + (size - 1));
        long end = System.nanoTime();
        logger.info("HashMap.containsKey 耗时 {} ns", end - start);
    }
}
