package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>72.生成子列表后不要再操作原列表
 *
 * {@link java.util.AbstractList.SubList} 的会检测修改计数器的方法，
 * 例如 size、set、get、add 等方法，
 * 若生成子列表后，再修改原列表，这些方法也会抛出 {@link java.util.ConcurrentModificationException}异常
 *
 * 对于子列表操作，因为视图是动态生成的，生成子列表后再操作原列表，必然会导致“视图”的不稳定，
 * 最有效的办法就是通过 {@link java.util.Collections#unmodifiableList(java.util.List)} 方法设置列表为只读状态
 *
 * 数据库的一张表可以有很多视图，
 * List也可以有多个视图，也就是可以有多个子列表，
 * 但问题是只要生成的子列表多于一个，
 * 则任何一个子列表就都不能修改了，否则就会抛出ConcurrentModificationException异常
 */
public class Ct {
}
