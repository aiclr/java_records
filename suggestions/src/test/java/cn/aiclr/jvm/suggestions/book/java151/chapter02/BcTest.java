package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BcTest {

    @Test
    @DisplayName("分别传递int类型和Integer类型")
    void test() {
        int i = 140;
        //int可以加宽转变成long，但不能直接转变成包装类型Long
        Assertions.assertEquals("基础数据类型", Bc.func(i));
        
        //i 通过 valueOf 方法包装成一个 Integer 对象
        //由于没有 f(Integer i) 方法，编译器“聪明”地把 Integer 对象拆箱成 int
        // int 自动拓宽为 long，编译结束。
        Assertions.assertEquals("基础数据类型", Bc.func(Integer.valueOf(i)));

        //i是一个int类型，不能自动转变为Long型
//        Bc.func2(i);
    }

}