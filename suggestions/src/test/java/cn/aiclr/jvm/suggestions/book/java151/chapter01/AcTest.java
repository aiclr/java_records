package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AcTest {

    /**
     * 三目运算符，并且第二、第三位操作数分别是基本类型和对象。
     * 所以对对象进行拆箱操作，由于该对象为null，
     * 所以在拆箱过程中调用 null.booleanValue() 的时候就报了 NPE (空指针)
     */
    @Test
    @DisplayName("三目运算NPE")
    void testNPE() {
        Integer a = null;
        Assertions.assertEquals(1, true ? 1 : a);
        Assertions.assertThrows(NullPointerException.class, () -> {
            Integer r = false ? 1 : a;
        });
    }

    /**
     * 三目运算符，并且第二、第三位操作数分别是基本类型和对象。
     * 所以对对象进行拆箱操作，由于该对象为null，
     * 所以在拆箱过程中调用 null.booleanValue() 的时候就报了 NPE (空指针)
     */
    @Test
    @DisplayName("三目运算空值")
    void testNull() {
        Integer a = null;
        Integer b = 1;
        Assertions.assertEquals(null, false ? b : a);
        Assertions.assertEquals(1, true ? b : a);
    }

    @Test
    @DisplayName("三目运算类型转换")
    void test() {
        int i = 80;
        String s = String.valueOf(i < 100 ? 90 : 100);
        String s1 = String.valueOf(i < 100 ? 90 : 100.0);
        Assertions.assertNotEquals(s, s1);
    }

}