package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>70.子列表只是原列表的一个视图
 *
 * 定义 {@link java.util.List#subList(int, int)} 其作用是返回一个列表的子列表
 * 注意:
 *      子列表只是一个视图，所有的修改动作直接作用于原列表
 * 实现 {@link java.util.AbstractList#subList(int, int)}
 * 根据是不是可以随机存取来提供不同的实现方式
 * <code> return (this instanceof RandomAccess ?
 * new RandomAccessSubList<>(this, fromIndex, toIndex) :
 * new SubList<>(this, fromIndex, toIndex));</code>
 * {@link java.util.AbstractList.RandomAccessSubList}
 * {@link java.util.AbstractList.SubList}，
 * 随机存储的使用频率比较高，而且 {@link java.util.AbstractList.RandomAccessSubList} 也是 {@link java.util.AbstractList.SubList} 子类，
 * 所以所有的操作都是由  {@link java.util.AbstractList.SubList} 实现的（除了自身的  {@link java.util.AbstractList.SubList} 方法外）
 *
 * subList方法的实现原理：
 * 返回的 subList 类 也是 {@link java.util.AbstractList} 的子类，
 * 其所有的方法如 get、set、add、remove 等都是在原始列表上的操作，
 * 它自身并没有生成一个数组或是链表，
 * 也就是子列表只是原列表的一个视图（View），所有的修改动作都反映在了原列表
 */
public class Cr {
}