package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <pre>71.推荐使用 {@link java.util.List#subList(int, int)} 处理局部列表
 *
 * 一个简单的需求：
 * 一个列表有 100 个元素，现在要删除索引位置为 20 ～ 30 的元素.
 *
 * “one-lining”一行代码就解决上述问题
 *
 * {@link java.util.ArrayList#removeRange(int, int)} 方法有 protected 关键字修饰着，不能直接使用
 * 使用 {@link java.util.List#subList(int, int)} 获取子集合，然后子集合执行{@link java.util.List#clear()}操作，操作结果影响到源集合。
 */
public class Cs {
    public static void main(String[] args) {
        //初始化固定长度不可变列表
        List<Integer> initData = Collections.nCopies(100, 0);
        //转换为可变列表
        ArrayList<Integer> list = new ArrayList<>(initData);
        System.out.println(list.size());
        //删除指定范围元素
        // 用subList先取出一个子列表，然后清空。
        // 因为 subList 返回的 List 是原始列表的一个视图，
        // 删除这个视图中的所有元素，最终就会反映到原始字符串上，那么一行代码即解决问题
        list.subList(20, 30).clear();
        System.out.println(list.size());
    }

}
