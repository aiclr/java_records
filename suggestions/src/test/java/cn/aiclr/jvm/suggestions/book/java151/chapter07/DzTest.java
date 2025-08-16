package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DzTest {

    /**
     * <pre>并没有对 {@link cn.aiclr.jvm.suggestions.book.java151.chapter07.Dz} 做任何初始化，
     * 只是通过 {@link java.lang.Class#forName(String)} 方法加载了 {@link cn.aiclr.jvm.suggestions.book.java151.chapter07.Dz} 类，
     * 但是却产生了一个 “Dz static block” 的输出，
     * 这就是因为 {@link cn.aiclr.jvm.suggestions.book.java151.chapter07.Dz} 类被加载后，
     * JVM 会自动初始化其 static 变量和 static 代码块，这是类加载机制所决定的
     */
    @Test
    @DisplayName("Class.forName")
    void test() {
        //动态加载
        try {
            Class clazz = Class.forName("cn.aiclr.jvm.suggestions.book.java151.chapter07.Dz");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
