package cn.aiclr.jvm.suggestions.book.java151.chapter05;

/**
 * <pre>81.非稳定排序推荐使用{@link java.util.List}
 *
 * 注意：{@link java.util.SortedSet} 中的元素被修改后可能会影响其排序位置
 *
 * {@link java.util.Set} 与 {@link java.util.List} 的最大区别就是 {@link java.util.Set} 中的元素不可以重复（这个重复指的是 元素 equals 为 true ），其他方面则没有太大的区别。
 * {@link java.util.Set} 的实现类中有一个比较常用的类需要了解一下：
 *      {@link java.util.TreeSet} 该类实现了类默认排序为升序的 {@link java.util.Set} 集合，
 *      如果插入一个元素，默认会按照升序排列（根据 {@link java.lang.Comparable#compareTo(Object)} 的返回值确定排序位置）
 *
 * {@link java.util.SortedSet} 接口（{@link java.util.TreeSet} 实现了该接口）
 * 只是定义了在给集合加入元素时将其进行排序，
 * 并不能保证元素修改后的排序结果，
 * 因此 {@link java.util.TreeSet} 适用于不变量的集合数据排序，比如 {@link java.lang.String}、{@link java.lang.Integer} 等使用 final 修饰的类型，
 * 但不适用于可变量的排序，特别是不确定何时元素会发生变化的数据集合
 *
 * 解决重排序
 * 1.{@link java.util.Set} 集合重排序: 重新生成 {@link java.util.Set} 对象
 * 2.彻底重构掉 {@link java.util.TreeSet} 使用 {@link java.util.List} 解决问题
 *      之所以使用 {@link java.util.TreeSet} 是希望实现自动排序，即使修改也能自动排序，
 *      既然它无法实现，那就用 {@link java.util.List} 来代替，然后再使用{@link java.util.Collections#sort(java.util.List)} 方法对 {@link java.util.List} 排序
 *
 * 建议
 *  对于不变量的排序，例如直接量（也就是8个基本类型）、{@link java.lang.String} 类型等，推荐使用 {@link java.util.TreeSet}，
 *  对于可变量，例如我们自己写的类，可能会在逻辑处理中改变其排序关键值的，则建议使用 {@link java.util.List} 自行排序
 *
 * 保证集合中元素的唯一性，又要保证元素值修改后排序正确
 * {@link java.util.List} 不能保证集合中的元素唯一，它是可以重复的，而 {@link java.util.Set} 能保证元素唯一，不重复。
 * 如果采用 {@link java.util.List} 解决排序问题，就需要自行解决元素重复问题（若要剔除也很简单，转变为 {@link java.util.HashSet},剔除后再转回来）。
 * 若采用 {@link java.util.TreeSet}，则需要解决元素修改后的排序问题，孰是孰非，就需要根据具体的开发场景来决定了
 */
public class Dc implements Comparable<Dc> {
    private int height;

    @Override
    public int compareTo(Dc o) {
        return height - o.height;
    }

    public Dc(int height) {
        this.height = height;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "height=" + height;
    }
}
