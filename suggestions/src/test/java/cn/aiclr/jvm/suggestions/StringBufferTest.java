package cn.aiclr.jvm.suggestions;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * String 字符串不经常变化的场景
 * <p>
 * StringBuffer 线程安全
 * 频繁进行字符串的拼接,替换,删除等,并且允许在多线程环境中, XML解析,HTTP参数解析和封装
 * <p>
 * StringBuilder 线程不安全
 * 频繁进行字符串的拼接,替换,删除等,并且运行在单线程的环境中,SQL拼接,JSON封装等
 */
class StringBufferTest {

    /**
     * 参考源代码 {@link java.lang.StringBuffer#append(String)}
     * 方法带 synchronized
     */
    @Test
    void testStringBuffer() {
        StringBuffer sb = new StringBuffer("a");
        sb.append("b");
        Assertions.assertEquals("ab", sb.toString());
    }

    /**
     * 参考源代码 {@link java.lang.StringBuilder#append(String)}
     * 方法不带 synchronized
     */
    @Test
    void testStringBuilder() {
        StringBuilder sb = new StringBuilder("a");
        sb.append("b");
        Assertions.assertEquals("ab", sb.toString());
    }

}