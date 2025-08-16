package cn.aiclr.jvm.suggestions.book.java151.chapter04;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.Collator;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Locale;

class CgTest {

    private static final Logger logger = LoggerFactory.getLogger(CgTest.class);

    /**
     * <pre>
     * Arrays 工具类的默认排序是通过数组元素的 compareTo 方法来进行比较的
     * String compareTo 源码:
     *  public int compareTo(String anotherString) {
     *         int len1 = value.length;
     *         int len2 = anotherString.value.length;
     *         int lim = Math.min(len1, len2);
     *         char v1[] = value;
     *         char v2[] = anotherString.value;
     *
     *         int k = 0;
     *         while (k < lim) {
     *             char c1 = v1[k];
     *             char c2 = v2[k];
     *             if (c1 != c2) {
     *                 return c1 - c2;
     *             }
     *             k++;
     *         }
     *         return len1 - len2;
     *     }
     * 这里是字符比较（减号操作符），也就是 UNICODE 码值的比较，
     * 查一下 UNICODE 代码表，“张”的码值是 5F20，而“李”是 674E，
     * 这样一看，“张”排在“李”的前面也就很正确了
     * 非英文的 String 排序可能会出现不准确的情况
     * 对非英文 String 排序 Java 推荐使用 Collator 类进行排序
     */
    @Test
    @DisplayName("Arrays.sort() 非英文排序不准确")
    void testArraysSort() {
        String[] strings = {"张三（Z）", "李四（L）", "王五（W）"};
        Arrays.sort(strings);
        Arrays.stream(strings).forEach(it -> logger.info("{}", it));
    }

    /**
     * <pre>Java 使用的是 UNICODE 编码，而中文 UNICODE 字符集是来源于 GB18030 的，
     * GB18030 又是从 GB2312 发展起来，
     * GB2312 是一个包含了 7000 多个字符的字符集，它是按照拼音排序，并且是连续的，
     * 之后的 GBK、GB18030 都是在其基础上扩充出来的，所以要让它们完整排序也就难上加难
     */
    @Test
    @DisplayName("非英文使用 Collator 排序")
    void testCollator() {
        String[] strings = {"张三（Z）", "李四（L）", "王五（W）"};
        //定义一个中文排序器
        Comparator<Object> comparator = Collator.getInstance(Locale.CHINA);
        //升序排列
//        Arrays.sort(strings, comparator);
        Arrays.stream(strings).sorted(comparator).forEach(it -> logger.info("{}", it));

    }

    /**
     * <pre>Java 使用的是 UNICODE 编码，而中文 UNICODE 字符集是来源于 GB18030 的，
     * GB18030 又是从 GB2312 发展起来，
     * GB2312 是一个包含了 7000 多个字符的字符集，它是按照拼音排序，并且是连续的，
     * 之后的 GBK、GB18030 都是在其基础上扩充出来的，所以要让它们完整排序也就难上加难
     */
    @Test
    @DisplayName("Collator 排序也不一定准确")
    void test() {
        String[] strings = {"犇（Ben）", "鑫（X）", "张三（Z）", "李四（L）", "王五（W）"};
        //定义一个中文排序器
        Comparator<Object> comparator = Collator.getInstance(Locale.CHINA);
        //升序排列
//        Arrays.sort(strings, comparator);
        Arrays.stream(strings).sorted(comparator).forEach(it -> logger.info("{}", it));
    }
}
