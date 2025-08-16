package cn.aiclr.jvm.suggestions.book.java151.chapter04;

/**
 * <pre>56.自由选择字符串拼接方法
 * 对一个字符串进行拼接有三种方法：
 *      加号（最常用）,最慢
 *          编译器对字符串的加号做了优化，它会使用 StringBuilder 的 append 方法进行追加，按道理来说，其执行时间也应该是0毫秒，
 *          不过它最终是通过 toString 方法转换成 String 字符串的
 *          {@code str1=new StringBuilder(str1).append("c").toString();}
 *          每次循环都会创建一个 StringBuilder 对象，
 *          二是每次执行完毕都要调用 toString 方法将其转换为字符串-----执行时间就是耗费在这里
 *      concat 方法，稍快
 *      StringBuilder（或StringBuffer）的 append 方法，最快
 *
 * 三者的实现方法不同，性能也就不同，但并不表示我们一定要使用 StringBuilder，
 * 这是因为 “+” 非常符合我们的编码习惯，适合人类阅读，
 * 两个字符串拼接，就用加号连一下，这很正常，也很友好，在大多数情况下我们都可以使用加号操作，
 * 只有在系统性能临界（如在性能 “增之一分则太长” 的情况下）的时候才可以考虑使用 concat 或 append 方法。
 * 而且，很多时候系统 80% 的性能是消耗在 20% 的代码上的，我们的精力应该更多的投入到算法和结构上
 */
public class Cd {
}
