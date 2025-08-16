package cn.aiclr.jvm.suggestions.book.java151.chapter04;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BzTest {

    @Test
    @DisplayName("字符串常量池")
    void test() {
        //先检查字符串常量池是否存在 "hello"，无则新增，有则返回 常量池中的地址
        String str1 = "hello";
        String str2 = "hello";
        Assertions.assertSame(str1, str2);

        //String 类是不可变的 final 修饰，其所有方法，有String 返回值的，不会对原对象进行修改，都会新建一个 String 对象
        str2 = str2 + "";
        Assertions.assertNotSame(str1, str2);
        Assertions.assertEquals(str1, str2);

        //直接 new String 创建 String 对象，不会检查字符串常量池，也不会把新建的 String 对象放到常量池中
        String str3 = new String("hello");
        Assertions.assertNotSame(str1, str3);
        Assertions.assertEquals(str1, str3);

        //intern 检查当前对象 str3 在对象池中是否存在字面量相同的对象
        //如果有则返回常量池中的对象
        //否则将当前对象 str3 放置到对象池中，并返回对象池中的对象
        String str4 = str3.intern();
        Assertions.assertSame(str1, str4);

        String str5 = new String("hi");
        String str6 = str5.intern();
        Assertions.assertNotSame(str5, str6);

        String str7 = "hi";
        Assertions.assertSame(str7, str6);

    }
}