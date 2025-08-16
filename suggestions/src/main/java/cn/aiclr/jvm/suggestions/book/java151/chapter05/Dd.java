package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>82.由点及面，一叶知秋—集合大家族
 *
 * 注意: <a href="https://mvnrepository.com/artifact/org.apache.commons/commons-collections4/">commons-collections</a>、<a href="https://mvnrepository.com/artifact/com.google.guava/guava/">google-collections(更名为 Guava)</a> 是JDK之外的优秀数据集合工具包，使用拿来主义即可
 *
 * Java 中的集合类丰富
 * 常用的 {@link java.util.ArrayList}、{@link java.util.HashMap}
 * 不常用的 {@link java.util.Stack}、{@link java.util.Queue}
 * 线程安全的 {@link java.util.Vector}、{@link java.util.Hashtable}、{@link java.util.concurrent.ConcurrentHashMap}
 * 线程不安全的 {@link java.util.LinkedList}、{@link java.util.TreeMap}
 * 阻塞式的 {@link java.util.concurrent.ArrayBlockingQueue}
 * 非阻塞式的 {@link java.util.PriorityQueue} 等
 *
 * 1.{@link java.util.List}
 *     实现 {@link java.util.List} 接口的集合主要有：
 *         {@link java.util.ArrayList} 是一个动态数组，
 *         {@link java.util.LinkedList}是一个双向链表，
 *         {@link java.util.Vector} 是一个线程安全的动态数组，
 *         {@link java.util.Stack} 是一个对象栈，遵循先进后出的原则
 * 2.{@link java.util.Set}
 *      {@link java.util.Set} 是不包含重复元素的集合，其主要的实现类有：
 *         {@link java.util.EnumSet} 是枚举类型的专用Set，所有元素都是枚举类型；
 *         {@link java.util.HashSet} 是以哈希码决定其元素位置的 Set，其原理与 {@link java.util.HashMap} 相似，它提供快速的插入和查找方法；
 *         {@link java.util.TreeSet} 是一个自动排序的 Set，它实现了 {@link java.util.SortedSet} 接口
 * 3.{@link java.util.Map}
 *     {@link java.util.Map} 是一个大家族，它可以分为排序 Map 和非排序 Map，
 *      排序 Map 主要是 {@link java.util.TreeMap} 类，它根据 Key 值进行自动排序；
 *      非排序 Map 主要包括：{@link java.util.HashMap}、{@link java.util.Hashtable}、{@link java.util.Properties}、{@link java.util.EnumMap} 等，
 *          {@link java.util.Properties} 是 {@link java.util.Hashtable} 的子类，它的主要用途是从 .properties 文件中加载数据，并提供方便的读写操作；
 *          {@link java.util.EnumMap} 则是要求其 Key 必须是某一个枚举类型。
 *          Map 中还有一个 {@link java.util.WeakHashMap} 类需要说明，它是一个采用弱键方式实现的 Map 类，
 *              它的特点是： {@link java.util.WeakHashMap} 对象的存在并不会阻止垃圾回收器对键值对的回收，
 *              也就是说使用 {@link java.util.WeakHashMap} 装载数据不用担心内存溢出的问题，
 *              GC会自动删除不用的键值对，这是好事。但也存在一个严重问题：
 *              GC是静悄悄回收的（何时回收？God knows!），我们的程序无法知晓该动作，存在着重大的隐患。
 * 4.{@link java.util.Queue}
 *      队列，它分为两类，
 *      一类是阻塞式队列，队列满了以后再插入元素则会抛出异常，主要包括：
 *              {@link java.util.concurrent.ArrayBlockingQueue} 是一个以数组方式实现的有界阻塞队列，
 *              {@link java.util.concurrent.PriorityBlockingQueue} 是依照优先级组建的队列，
 *              {@link java.util.concurrent.LinkedBlockingQueue} 是通过链表实现的阻塞队列；
 *      另一类是非阻塞队列，无边界的，只要内存允许，都可以持续追加元素，
 *          我们最经常使用的是 {@link java.util.PriorityQueue} 类。
 *      还有一种队列，是双端队列，支持在头、尾两端插入和移除元素，它的主要实现类是：
 *          {@link java.util.ArrayDeque}、{@link java.util.concurrent.LinkedBlockingDeque}、{@link java.util.LinkedList}
 * 5.数组
 *      数组与集合的最大区别就是数组能够容纳基本类型，而集合就不行，
 *      更重要的一点就是所有的集合底层存储的都是数组
 * 6.工具类
 *      数组的工具类是 {@link java.util.Arrays} 和{@link java.lang.reflect.Array}
 *      集合的工具类是{@link java.util.Collections}
 * 7.扩展类
 *      集合类可以自行扩展，想写一个自己的 List？没问题，但最好的办法还是“拿来主义”，
 *      可以使用 Apache 的 <a href="https://mvnrepository.com/artifact/org.apache.commons/commons-collections4/">commons-collections</a> 扩展包，
 *      也可以使用 Google 的 <a href="https://mvnrepository.com/artifact/com.google.guava/guava/">google-collections(更名为 Guava)</a> 扩展包，这些足以应对我们的开发需要
 */
public class Dd {
}
