package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>79.集合中的哈希码不要重复
 *
 * jdk7
 * 数据结构：使用数组加链表的形式存储元素。每个数组位置被称为一个桶（bucket），如果多个键映射到同一个桶，则这些键值对以链表形式链接。
 * 哈希冲突处理：当发生哈希冲突时，新元素会添加到链表的头部，这种做法称为头插法。这种方式可能导致在并发情况下出现环形链表，从而导致遍历时的死循环。
 * 扩容机制：当 HashMap 中的元素数量超过负载因子与容量的乘积时，HashMap 会进行扩容操作，并重新计算所有元素的位置。
 *
 * jdk8
 * 数据结构：在数组加链表的基础上引入了红黑树。当链表长度达到一定阈值（默认为8）时，链表会被转换成红黑树，以便提高查找效率。
 * 哈希冲突处理：对于新的插入操作，改为尾插法，即新元素被添加到链表的末尾，减少了形成环形链表的风险。
 * 扩容机制：改进了扩容机制，采用了渐进式扩容的方式，在扩容过程中逐步迁移元素，降低了对其他线程的影响。
 * 性能优化：简化了 hash 函数，提高了性能。同时，由于引入了红黑树，查询时间复杂度从 O(n) 提升到了 O(log n)，特别是在哈希冲突较多的情况下。
 *
 * 注意：{@link java.util.HashMap}中对象的 {@link java.lang.Object#hashCode()} 应避免冲突
 *
 * 在一个列表中查找某值是非常耗费资源的，
 *      随机存取的列表是遍历查找，
 *      顺序存储列表是链表查找，
 *      {@link java.util.Collections#binarySearch(java.util.List, Object)} 的二分法查找，
 * 但这些都不够快，毕竟都是遍历，
 * 最快的还要数以 Hash 开头的集合（如 {@link java.util.HashMap}、{@link java.util.HashSet}等类）查找
 *
 * {@link java.util.HashMap}的存储主线还是数组，遇到哈希冲突的时候则使用链表解决。
 * 了解了 {@link java.util.HashMap} 是如何存储的：使用 hashCode 定位元素，若有哈希冲突，则遍历对比，
 * 在没有哈希冲突的情况下，{@link java.util.HashMap} 的查找则是依赖 hashCode 定位的，因为是直接定位，那效率当然就高
 *
 * 如果哈希码相同，它的查找效率就与 {@link java.util.ArrayList} 没什么两样了，遍历对比，性能会大打折扣。
 * 特别是在那些进度紧张的项目中，虽重写了 hashCode 方法但返回值却是固定的，此时如果把这些对象插入到 {@link java.util.HashMap} 中，查找就相当耗时
 */
public class Da {
}
