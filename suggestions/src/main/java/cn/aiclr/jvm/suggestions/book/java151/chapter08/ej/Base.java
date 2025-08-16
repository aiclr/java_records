package cn.aiclr.jvm.suggestions.book.java151.chapter08.ej;

import java.io.IOException;

/**
 * 3)构造函数尽可能不要抛出受检异常
 */
public class Base {

    /**
     * 父类 构造函数 抛出 IOException
     *
     * @throws IOException
     */
    public Base() throws IOException {
        throw new IOException();
    }

    /**
     * 普通方法
     *
     * @throws Exception
     */
    public void method() throws Exception {
    }

}
