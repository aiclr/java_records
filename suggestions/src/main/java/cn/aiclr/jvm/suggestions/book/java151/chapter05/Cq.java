package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>69.列表相等只需关心元素数据
 *  注意：判断集合是否相等时只须关注元素是否相等即可
 *
 * 只要实现 {@link java.util.List} 接口，不关心具体实现类。
 * 只要所有的元素相等，并且长度也相等就表明两个 {@link java.util.List} 是相等的，
 * 与具体的容量类型无关。
 * 例子中虽然一个是 {@link java.util.ArrayList}，一个是 {@link java.util.Vector}，只要里面的元素相等，那结果就是相等
 * 其他的集合类型，如 {@link java.util.Set}、{@link java.util.Map} 等与此相同，也是只关心集合元素，不用考虑集合类型。
 */
public class Cq {
}
