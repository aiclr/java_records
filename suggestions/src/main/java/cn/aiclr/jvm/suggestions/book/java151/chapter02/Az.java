package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import java.util.List;

/**
 * <pre>26.提防包装类型的 null 值
 *
 * Java引入包装类型（Wrapper Types）是为了解决基本类型的实例化问题，以便让基本类型也能参与到面向对象的编程世界中。
 * 而在Java5中泛型更是对基本类型说了“不”，如想把一个整型放到 List 中，就必须使用 Integer 包装类型
 *
 * 包装对象和拆箱对象可以自由转换，但是要剔除 null 值，null值并不能转化为基本类型。
 * 对于此类问题，我们谨记一点：包装类型参与运算时，要做 null 值校验
 */
public class Az {

    /**
     * for循环中，隐含了一个拆箱过程，
     * 在此过程中包装类型 Integer 转换为了基本类型 int。
     * 我们知道拆箱过程是通过调用包装对象的 intValue 方法来实现的，
     * 由于包装对象是 null 值，访问其 intValue 方法报空指针异常也就在所难免了。
     * 问题清楚了，修改也很简单，加入 null 值检查即可
     */
    public static int func(List<Integer> list) {
        int count = 0;
        for (Integer i : list) {
            count += (i != null) ? i : 0;
        }
        return count;
    }

    public static int funcErr(List<Integer> list) {
        int count = 0;
        for (Integer i : list) {
            count += i;
        }
        return count;
    }
}