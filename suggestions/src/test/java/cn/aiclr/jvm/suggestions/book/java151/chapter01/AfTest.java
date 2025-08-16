package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AfTest {

    @Test
    @DisplayName("向上转型")
    void testUp() {
        //subAfBase 对象把子类 SubAfBase 对象做了向上转型，
        // 形参列表是由父类决定的，即变长数组,编译时 subAfBase.fun(100,50) 中的 50会被编译成 [50]数组，再由子类执行
        AfBase subAfBase = new SubAfBase();
        Assertions.assertEquals("sub", subAfBase.fun(100, 50));

        AfBase subAfBase1 = new SubAfBase1();
        Assertions.assertEquals("sub1", subAfBase1.fun(100, 50));
    }

    @Test
    @DisplayName("正确的覆写父类方法的方式")
    void test() {
        //不转型，不正确的覆写父类方法的方式
        SubAfBase subAfBase = new SubAfBase();
        Assertions.assertEquals("sub", subAfBase.fun(100, new int[]{50}));

        //不转型，正确的覆写父类方法的方式
        SubAfBase1 subAfBase1 = new SubAfBase1();
        Assertions.assertEquals("sub1", subAfBase1.fun(100, 50));
    }
}