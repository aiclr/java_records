package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>66.{@link java.util.Arrays#asList(Object[])} 方法产生的 List 对象不可更改
 * 注意：除非非常自信该 List 只用于读操作，否则不可以使用 {@link java.util.Arrays#asList(Object[])} 初始化 List
 *
 * {@link java.util.Arrays#asList(Object[])} 直接 new 一个静态私有内部类 {@link java.util.Arrays.ArrayList}
 *      {@code private static class ArrayList<E> extends AbstractList<E>}
 * 此 {@link java.util.Arrays.ArrayList} 非 {@link java.util.ArrayList} 而是 {@link java.util.Arrays} 工具类的一个内置类
 * 这里的 {@link java.util.Arrays.ArrayList} 是一个静态私有内部类，除了 {@link java.util.Arrays} 能访问外，其他类都不能访问。
 * 这个类没有提供 add、remove 方法，父类 {@link java.util.AbstractList} 提供方法但没有提供具体的实现直接抛出异常
 *      {@code public void add(int index, E element) { throw new UnsupportedOperationException();}}
 *      {@code public E remove(int index) {throw new UnsupportedOperationException();}}
 * {@link java.util.Arrays.ArrayList} 静态内部类，它没有重写父类 {@link java.util.AbstractList#add(Object)} 和 {@link java.util.AbstractList#remove(Object)}方法
 *
 * 也就是说 {@link java.util.Arrays#asList(Object[])} 返回的是一个长度不可变的列表，
 * 数组是多长，转换成的列表也就是多长，
 * 换句话说此处的列表只是数组的一个外壳，不再保持列表动态变长的特性
 */
public class Cn {
}