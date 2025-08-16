package cn.aiclr.jvm.suggestions.book.java151.chapter04;

/**
 * <pre>52.推荐使用 String 直接量赋值
 * 一般对象都是通过 new 关键字生成的，
 * 但是 String 还有第二种生成方式，
 * 也就是我们经常使用的直接声明方式，比如{@code String str = "a";}
 * 即是通过直接量 “a” 进行赋值的。
 * 对于 String 对象来说，这种方式是极力推荐的，
 * 不建议使用 {@code new String("a");} 的方式赋值
 *
 * Java 为了避免在一个系统中大量产生 String 对象（为什么会大量产生？因为 String 字符串是程序中最经常使用的类型），
 * 设计了一个字符串池（也有叫做字符串常量池，String Pool 或 String Constant Pool 或 String Literal Pool），
 * 在字符串池中所容纳的都是 String 字符串对象，它的创建机制是这样的：
 *      创建一个字符串时，首先检查池中是否有字面值相等的字符串，
 *      如果有，则不再创建，直接返回池中该对象的引用，
 *      若没有则创建之，然后放到池中，并返回新建对象的引用，
 *      这个池和我们平常所说的池概念非常相似。
 * 直接声明一个 String 对象是不检查字符串池的，也不会把对象放到池中，那当然 “==” 为false
 * intern 会检查当前的对象在对象池中是否有字面值相同的引用对象，
 *      如果有则返回池中对象，
 *      如果没有则放置到对象池中，并返回对象池中的对象
 * 线程安全：
 * String 类是一个不可变（Immutable）对象其实有两层意思：
 *      一是 String 类是 final 类，不可继承，不可能产生一个 String 的子类；
 *      二是在 String 类提供的所有方法中，如果有 String 返回值，就会新建一个 String 对象，不对原对象进行修改，这也就保证了原对象是不可改变的。
 *
 * Java 的每个对象都保存在堆内存中，
 * 但是字符串池非常特殊，它在编译期已经决定了其存在 JVM 的常量池（Constant Pool），
 * 垃圾回收器是不会对它进行回收的
 */
public final class Bz {
}
