package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class CmTest {

    @Test
    void test() {
        int[] data = {1, 2, 3, 4, 5};

        /**
         * 把一个 int 类型的数组作为了 T 的类型
         * 所以转换后在 List 中就只有一个类型为 int 数组的元素
         */
        List list = Arrays.asList(data);

        Assertions.assertSame(1, list.size());
        Assertions.assertSame(data, list.get(0));


        list = List.of(data);
        Assertions.assertSame(1, list.size());
        Assertions.assertSame(data, list.get(0));


        Integer[] data2 = {1, 2, 3, 4, 5};
        list = Arrays.asList(data2);
        Assertions.assertSame(5, list.size());

        list = List.of(data2);
        Assertions.assertSame(5, list.size());
    }
}
