package cn.aiclr.jvm.suggestions.book.java151.chapter04;

/**
 * <pre>54.正确使用 String、StringBuffer、StringBuilder
 *
 * CharSequence 接口有三个实现类与字符串有关：String、StringBuffer、StringBuilder
 * 虽然它们都与字符串有关，但是其处理机制是不同的。
 * 1. String 类是不可改变的类，也就是创建后就不能再修改了，
 *      比如创建了一个 “abc” 这样的字符串对象，那么它在内存中永远都会是 “abc” 这样具有固定表面值的一个对象，
 *      不能被修改，即使想通过 String 提供的方法来尝试修改，也是要么创建一个新的字符串对象，要么返回自己
 *      当采用 str.substring(0) 就不会创建新对象，JVM会从字符串池中返回str的引用，也就是自身的引用
 * 2. StringBuffer 是一个可变字符序列，
 *      它与 String 一样，在内存中保存的都是一个有序的字符序列（char 类型的数组），
 *      不同点是 StringBuffer 对象的值是可改变的
 * 3. StringBuilder 与 StringBuffer 基本相同，都是可变字符序列，
 *      不同点是：StringBuffer 是线程安全的，StringBuilder 是线程不安全的，
 *      翻翻两者的源代码，就会发现在 StringBuffer 的方法前都有 synchronized 关键字，
 *      这也是 StringBuffer 在性能上远低于 StringBuilder 的原因
 *
 * 使用场景：
 *  1. String: 字符串不经常变化的场景中可以使用 String 类，例如常量的声明、少量的变量运算等
 *  2. StringBuffer: 频繁进行字符串的运算（如拼接、替换、删除等），并且运行在多线程的环境中，则可以考虑使用 StringBuffer，例如 XML 解析、HTTP 参数解析和封装等
 *  3. StringBuilder: 频繁进行字符串的运算（如拼接、替换、删除等），并且运行在单线程的环境中，则可以考虑使用 StringBuilder，如 SQL 语句的拼装、JSON 封装等
 */
public class Cb {
}
