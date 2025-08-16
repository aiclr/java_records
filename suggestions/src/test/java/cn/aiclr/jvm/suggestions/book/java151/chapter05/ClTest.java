package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class ClTest {

    private static final Logger logger = LoggerFactory.getLogger(ClTest.class);

    @Test
    void test() {
        int[] data = {1, 2, 3, 4, 5, 6, 7, 1};
        logger.info("直接循环查找：{}", Cl.max(data));
        logger.info("先排序再查找：{}", Cl.max2(data));
        Integer[] data3 = {1, 1, 1, 2, 6, 4, 2, 6, 1, 2, 3, 4, 5, 6, 7, 1};
        logger.info("先去重、再排序、再查找第二大：{}", Cl.max3(data3));
    }
}
