package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>67.不同的列表选择不同的遍历方法
 *
 * 1.Java 为 {@link java.util.ArrayList} 类加上了 {@link java.util.RandomAccess} 接口，就是在告诉我们 {@link java.util.ArrayList} 支持快速随机访问（即常数时间 O(1) 的 get(int index) 操作）.采用下标方式遍历列表速度会更快
 * 2.{@link java.util.LinkedList} 实现了双向链表，
 *      每个数据结点 {@link java.util.LinkedList.Node} 中都有三个数据项：前节点的引用（prev）、本节点元素（item）、后继节点的引用（next），
 *      也就是说在 {@link java.util.LinkedList} 中的两个元素本来就是有关联的，我知道你的存在，你也知道我的存在。
 *      元素之间已经有关联关系了，使用 foreach 也就是迭代器方式效率挺高，使用下标方式效率极低.每次调用 {@link java.util.LinkedList#get(int)} 都会遍历集合。故不要使用 for-i遍历
 *
 * 在Java中，{@link java.util.RandomAccess} 和 {@link java.lang.Cloneable}、{@link java.io.Serializable} 一样，都是标志性接口，不需要任何实现，只是用来表明其实现类具有某种特质的，
 *      {@link java.util.RandomAccess} 表明可以被拷贝，
 *      {@link java.io.Serializable} 接口表明被序列化了，
 *      {@link java.lang.Cloneable} 则表明这个类可以随机存取，
 */
public class Co {
}
