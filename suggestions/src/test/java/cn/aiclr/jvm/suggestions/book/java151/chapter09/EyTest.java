package cn.aiclr.jvm.suggestions.book.java151.chapter09;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

class EyTest {

    /**
     * <pre>列表的初始长度为 5，在实际使用时，
     * 当加入的元素超过初始容量时，ArrayList 会自行扩容，确保能够正常加入元素
     */
    @Test
    @DisplayName("ArrayList 会自行扩容，确保能够正常加入元素")
    void testArrayList() {
        List<String> list = new ArrayList<>(5);
        for (int i = 0; i < 10; i++) {
            list.add("" + i);
        }
        Assertions.assertEquals(10, list.size());
    }

    /**
     * BlockingQueue 不能自行扩容，抛出下面的异常
     * Exception in thread "main" java.lang.IllegalStateException: Queue full
     */
    @Test
    @DisplayName("BlockingQueue 不能自行扩容")
    void testBlockQueue() {
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(5);
        for (int i = 0; i < 5; i++) {
            bq.add("" + i);
        }
        Assertions.assertEquals(5, bq.size());
        Assertions.assertThrows(IllegalStateException.class, () -> bq.add("6"));
    }
}
