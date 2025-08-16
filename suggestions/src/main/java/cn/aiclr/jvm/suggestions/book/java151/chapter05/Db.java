package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>80.多线程使用 {@link java.util.Vector} 或 {@link java.util.Hashtable}
 *
 * {@link java.util.Vector} 线程安全的动态数组（Array），允许 null 元素。
 * {@link java.util.Hashtable} 线程安全的哈希表（Hash Table），不允许 null 键和值。
 *
 * 由于 {@link java.util.Vector} 和 {@link java.util.Hashtable} 使用的是粗粒度的同步机制，在现代 Java 并发编程中，推荐使用以下替代方案：
 *
 * {@link java.util.Vector} --> {@link java.util.Collections#synchronizedList(java.util.List)}
 * {@link java.util.Hashtable} --> {@link java.util.concurrent.ConcurrentHashMap}（高性能并发哈希表）
 * 只读/写少读多场景 --> {@link java.util.concurrent.CopyOnWriteArrayList} / {@link java.util.concurrent.CopyOnWriteArraySet}
 *
 * {@link java.util.Vector}
 * 所有的方法都使用了 synchronized 关键字进行同步。
 * 比如：add(), get(), remove() 等方法都是线程安全的。
 * 但由于是粗粒度锁（整个对象锁），在高并发下性能较差。
 *
 * {@link java.util.Hashtable}
 * 同样所有方法都被 synchronized 修饰。
 * 线程安全，但同样存在性能瓶颈。
 * 不允许 null 键或 null 值，否则抛出 {@link java.lang.NullPointerException}
 *
 * {@link java.util.Vector} 初始容量默认10，扩容方式默认翻倍（可自定义增量）
 * {@link java.util.Hashtable} 初始容量默认11，扩容方式扩容为原来的 2*size +1
 *
 * 基本上所有的集合类都有一个叫做快速失败（Fail-Fast）的校验机制，
 * 当一个集合在被多个线程修改并访问时，就可能会出现 {@link java.util.ConcurrentModificationException} 异常，
 * 这是为了确保集合方法一致而设置的保护措施，
 * 它的实现原理就是我们经常提到的 modCount 修改计数器：
 * 如果在读列表时，modCount 发生变化（也就是有其他线程修改）则会抛出 {@link java.util.ConcurrentModificationException} 异常。
 * 这与线程同步是两码事，线程同步是为了保护集合中的数据不被脏读、脏写而设置的。
 *
 * {@link java.util.Vector} 和 {@link java.util.Hashtable} 的迭代器不是 fail-fast 的，因此在多线程环境下可能引发不一致状态。
 */
public class Db {
}
