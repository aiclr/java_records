package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AsTest {
    private static As as;

    @BeforeAll
    static void init() {
        as = new As();
    }

    @Test
    @DisplayName("在对外公开的方法中 不要使用 assert")
    void encode() {
        Assertions.assertEquals("123", As.encode(""));
        Assertions.assertThrows(AssertionError.class, () -> As.encode(null));
    }

    @Test
    @DisplayName("在执行逻辑代码的情况下 不要使用 assert")
    void doSomething() {
        List<String> list = Arrays.asList("item1", "item2");
        Assertions.assertThrows(AssertionError.class, () -> as.doSomething(list, "item3"));
    }

    @Test
    @DisplayName("程序探针可以使用 assert")
    void sayOk() {
        Assertions.assertThrows(AssertionError.class, () -> as.sayOk());
    }
}