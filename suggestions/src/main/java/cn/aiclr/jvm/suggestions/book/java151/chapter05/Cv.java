package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import cn.aiclr.jvm.suggestions.utils.ListUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>72.不推荐使用 {@link java.util.Collections#binarySearch(java.util.List, Object)} 对列表进行检索
 *
 * 使用 {@link java.util.Collections#binarySearch(java.util.List, Object)} 首先要考虑排序问题
 *      使用 {@link java.util.Collections#binarySearch(java.util.List, Object)} 的二分法查找比 {@link java.util.List#indexOf(Object)} 的遍历算法性能上高很多，
 *      特别是在大数据集而且目标值又接近尾部时，{@link java.util.Collections#binarySearch(java.util.List, Object)} 方法与 {@link java.util.List#indexOf(Object)} 相比，性能上会提升几十倍，
 *      因此在从性能的角度考虑时可以选择 {@link java.util.Collections#binarySearch(java.util.List, Object)}
 *
 * 对一个列表进行检索时，我们使用得最多的是 {@link java.util.List#indexOf(Object)} 方法，
 * 它简单、好用，而且也不会出错，虽然它只能检索到第一个符合条件的值，
 * 但是我们可以生成子列表后再检索，这样也就可以查找出所有符合条件的值了
 *
 * {@link java.util.Collections#binarySearch(java.util.List, Object)}
 * 该方法对一个列表进行检索，可查找出指定值的索引值
 * JDK 上对其描述：
 *      使用二分搜索法搜索指定列表，以获得指定对象。
 *      其实现的功能与 {@link java.util.List#indexOf(Object)} 是相同的，只是使用的是二分法搜索列表.
 */
public class Cv {
    public static void main(String[] args) {
        List<String> data = new ArrayList<>();
        data.add("A");
        data.add("B");
        data.add("C");
        data.add("A");
        data.add("E");
        data.add("F");
        data.add("A");
        data.add("H");
        data.add("A");
        data.add("J");
        data.add("K");
        data.add("Z");

        List<Integer> result = new ArrayList<>();
        ListUtils.getIndex(data, "A", result);
        System.out.println(result);//0,3,6,8

        //二分法查询的一个首要前提是：数据集已经实现升序排列，否则二分法查找的值是不准确的
        System.out.println(data);
        Collections.sort(data);
        System.out.println(data);

        //拷贝一个数组，然后再排序，再使用 binarySearch 查找指定值
        int index2 = Collections.binarySearch(data, "A");
        System.out.println(index2);

        index2 = Collections.binarySearch(data, "Z");
        System.out.println(index2);
    }
}
