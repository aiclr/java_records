package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class DiTest {

    public static List<String> data;

    @BeforeAll
    static void init() {
        //复习66. Cn asList方法产生的List对象不可更改
        data = Arrays.asList("Spring", "summer", "Winter");
    }

    @Test
    void testException() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Di.searchException(data));
    }

    @Test
    void testEnumExpand() {
        Di.searchEnumExpand(data);
    }

    @Test
    void test() {
        Di.search(data);
    }

}
