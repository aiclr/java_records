package cn.aiclr.jvm.suggestions.book.java151.chapter04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CbTest {

    @Test
    @DisplayName("String 不可变")
    void testString() {
        String str = "abc";

        int strHashCode = str.hashCode();

        //不会创建新对象
        String str2 = str.substring(0);
        Assertions.assertSame(str, str2);

        //创建新对象
        String str1 = str.substring(1);
        Assertions.assertNotSame(str, str1);

        //经过 + 操作后，str 变量的引用 变成新字符串 "abcd" 的引用
        str = str + "d";
        int strHashCode1 = str.hashCode();
        Assertions.assertNotEquals(strHashCode, strHashCode1);
        //经过 + 操作后，字符串常量池中 "abc" 对象不会发生改变，体现 String final 修饰的不可变类的属性
        int strHashCode2 = "abc".hashCode();
        Assertions.assertEquals(strHashCode, strHashCode2);

    }

    @Test
    @DisplayName("StringBuffer 线程安全的字符序列")
    void testStringBuffer() {
        StringBuffer sb = new StringBuffer();
        sb.append("a");
        sb.append(",");
        sb.append("b");
        sb.append(",");
        sb.deleteCharAt(sb.lastIndexOf(","));
        Assertions.assertEquals("a,b", sb.toString());
    }

    /**
     * <pre>StringBuilder 与 StringBuffer 基本相同，都是可变字符序列
     * 不同点：
     *      StringBuffer 是线程安全的（StringBuffer 的方法前都有 synchronized 关键字）
     *      StringBuilder 是线程不安全的，
     *
     * StringBuffer 在性能上远低于 StringBuilder 的原因
     *      StringBuffer 的方法前都有 synchronized 关键字，
     */
    @Test
    @DisplayName("StringBuilder 非线程安全 但高效的字符序列")
    void testStringBuilder() {
        StringBuilder sb = new StringBuilder();
        sb.append("a");
        sb.append(",");
        sb.append("b");
        sb.append(",");
        sb.deleteCharAt(sb.lastIndexOf(","));
        Assertions.assertEquals("a,b", sb.toString());
    }
}
