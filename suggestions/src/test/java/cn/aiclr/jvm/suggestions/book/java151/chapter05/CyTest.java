package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

@Execution(ExecutionMode.SAME_THREAD)
class CyTest {

    private static final Logger logger = LoggerFactory.getLogger(CyTest.class);

    private static final int size = 4;

    private static List<String> list = null;

    @BeforeEach
    void init() {
        list = new ArrayList<>(size);
        list.add("123");
        list.add("456");
        list.add("789");
        list.add("000");
    }

    @Test
    @DisplayName("Collections.shuffle 打乱 List")
    void testShuffle() {
        logger.info("shuffle-->{}", list);
        Collections.shuffle(list);
        logger.info("shuffle-->{}", list);
    }

    @Test
    @DisplayName("随机数打乱 List")
    void testRandom() {
        logger.info("random-->{}", list);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int randomPosition = random.nextInt(size);
            String temp = list.get(i);
            list.set(i, list.get(randomPosition));
            list.set(randomPosition, temp);
        }
        logger.info("random-->{}", list);
    }

    @Test
    @DisplayName("Collections.swap交换位置")
    void testSwap() {
        logger.info("swap-->{}", list);
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            int randomPosition = random.nextInt(size);
            Collections.swap(list, i, randomPosition);
        }
        logger.info("swap-->{}", list);
    }
}
