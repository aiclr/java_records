package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>65.避开基本类型数组转换列表陷阱
 *
 * 注意：原始类型数组不能作为 {@link java.util.Arrays#asList(Object[])} 的输入参数，否则会引起程序逻辑混乱
 *
 * 在 Java 中，数组是一个对象，它是可以泛型化的，
 * 也就是说我们的例子是把一个 int 类型的数组作为了 T 的类型，所以转换后在 List 中就只有一个类型为 int 数组的元素了
 * 不仅仅是 int 类型的数组有这个问题，其他 7 个基本类型的数组也存在相似的问题，
 * 在把基本类型数组转换成列表时，要特别小心 {@link java.util.Arrays#asList(Object[])} 方法的陷阱，避免出现程序逻辑混乱的情况
 */
public class Cm {
}
