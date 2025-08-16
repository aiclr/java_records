package cn.aiclr.jvm.suggestions.book.java151.chapter04;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class CdTest {

    private static final Logger logger = LoggerFactory.getLogger(CdTest.class);

    String str;

    @BeforeEach
    void beforeEach() {
        str = "a";
    }

    @Test
    @DisplayName("加号拼接字符串5万次")
    void testPlus() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            //str=new StringBuilder(str1).append("c").toString();
            //每次循环都会创建一个StringBuilder对象，二是每次执行完毕都要调用toString方法将其转换为字符串—它的执行时间就是耗费在这里
            str += "c";
        }
        long end = System.currentTimeMillis();
        logger.info("加号拼接字符串5万次，耗时={}ms", end - start);
    }

    @Test
    @DisplayName("concat() 方法拼接字符串5万次")
    void testConcat() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            /**
             * 源码:
             *  public String concat(String str) {
             *         int otherLen = str.length();
             *         if (otherLen == 0) {
             *             return this;
             *         }
             *         int len = value.length;
             *         char buf[] = Arrays.copyOf(value, len + otherLen);
             *         str.getChars(buf, len);
             *         return new String(buf, true);
             *     }
             * 整体看上去就是一个数组拷贝，
             * 虽然在内存中的处理都是原子性操作，速度非常快，
             * 不过，最后的 return 语句，每次的 concat 操作都会新创建一个 String 对象，
             * 这就是 concat 速度慢下来的真正原因，它创建了 5 万个 String 对象
             */
            str = str.concat("c");
        }
        long end = System.currentTimeMillis();
        logger.info("concat() 方法拼接5万次，耗时={}ms", end - start);
    }

    @Test
    @DisplayName("StringBuffer append 5万次")
    void testStringBuffer() {
        long start = System.currentTimeMillis();
        StringBuffer sb = new StringBuffer(str);
        for (int i = 0; i < 50000; i++) {
            /**
             * 源码：
             * public AbstractStringBuilder append(String str) {
             *         if (str == null)
             *             return appendNull();
             *         int len = str.length();
             *         ensureCapacityInternal(count + len);
             *         str.getChars(0, len, value, count);
             *         count += len;
             *         return this;
             *     }
             *  整个append方法都在做字符数组处理，加长，然后数组拷贝，
             *  这些都是基本的数据处理，没有新建任何对象，所以速度也就最快
             */
            sb.append("c");
        }
        long end = System.currentTimeMillis();
        logger.info("StringBuffer append 5万次，耗时={}ms", end - start);
    }

    @Test
    @DisplayName("StringBuilder append 5万次")
    void testStringBuild() {
        long start = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0; i < 50000; i++) {
            /**
             * 源码：
             * public AbstractStringBuilder append(String str) {
             *         if (str == null)
             *             return appendNull();
             *         int len = str.length();
             *         ensureCapacityInternal(count + len);
             *         str.getChars(0, len, value, count);
             *         count += len;
             *         return this;
             *     }
             *  整个append方法都在做字符数组处理，加长，然后数组拷贝，
             *  这些都是基本的数据处理，没有新建任何对象，所以速度也就最快
             */
            sb.append("c");
        }
        long end = System.currentTimeMillis();
        logger.info("StringBuilder append 5万次，耗时={}ms", end - start);
    }


}
