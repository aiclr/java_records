package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

class CxTest {

    private static final Logger logger = LoggerFactory.getLogger(CxTest.class);

    private final List<String> list1 = new ArrayList<>();
    private final List<String> list2 = new ArrayList<>();

    @BeforeEach
    void beforeEach() {
        list1.add("A");
        list1.add("B");//数学上规定集合内元素是不能重复的
        list1.add("B");//数学上规定集合内元素是不能重复的

        list2.add("C");
        list2.add("B");//数学上规定集合内元素是不能重复的
        list2.add("B");//数学上规定集合内元素是不能重复的
        list2.add("B");//数学上规定集合内元素是不能重复的
    }

    @Test
    @DisplayName("交集")
    void testAnd() {
        list1.retainAll(list2);
        logger.info("交集：{}", list1);
    }

    @Test
    @DisplayName("并集")
    void testOr() {
        list1.addAll(list2);
        logger.info("并集：{}", list1);

    }

    @Test
    @DisplayName("差集")
    void testNot() {
        list1.removeAll(list2);
        logger.info("差集：{}", list1);
    }

    @Test
    @DisplayName("无重复并集")
    void testOrNot() {
        //先删除 list2 里 list1 中出现的元素
        list2.removeAll(list1);
        logger.info("2:{}", list2);
        //再把剩下的 list2 并到 list1
        list1.addAll(list2);
        logger.info("无重复并集{}", list1);
    }
}
