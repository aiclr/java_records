package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BqTest {

    private static final Logger logger = LoggerFactory.getLogger(BqTest.class);

    @Test
    @DisplayName("浅拷贝")
    void shadowCloneTest() throws CloneNotSupportedException {
        Bq hello = new Bq("hello");
        BqShadowClone caddy = new BqShadowClone(10, "Caddy", hello);
        BqShadowClone clone = caddy.clone();
        show(caddy, clone);

        Assertions.assertEquals(caddy.age, clone.age);
        clone.age = 11;
        Assertions.assertNotEquals(caddy.age, clone.age);
        show(caddy, clone);

        Assertions.assertSame(caddy.name, clone.name);
        Assertions.assertEquals(caddy.name, clone.name);
        clone.name = clone.name + "_clone";
        Assertions.assertNotSame(caddy.name, clone.name);
        Assertions.assertNotEquals(caddy.age, clone.age);
        show(caddy, clone);

        Assertions.assertSame(caddy.bq, clone.bq);
        Assertions.assertEquals(caddy.bq, clone.bq);
        clone.bq.msg = "hi";
        Assertions.assertSame(caddy.bq, clone.bq);
        Assertions.assertEquals(caddy.bq, clone.bq);
        show(caddy, clone);
    }

    @Test
    @DisplayName("深拷贝")
    void deepCloneTest() throws CloneNotSupportedException {
        Bq hello = new Bq("hello");
        BqDeepClone caddy = new BqDeepClone(10, "Caddy", hello);
        BqDeepClone clone = caddy.clone();
        show(caddy, clone);

        Assertions.assertSame(caddy.age, clone.age);
        clone.age = 11;
        Assertions.assertNotSame(caddy.age, clone.age);
        show(caddy, clone);

        Assertions.assertSame(caddy.name, clone.name);
        Assertions.assertEquals(caddy.name, clone.name);
        clone.name = clone.name + "_clone";
        Assertions.assertNotSame(caddy.name, clone.name);
        Assertions.assertNotEquals(caddy.age, clone.age);
        show(caddy, clone);

        Assertions.assertNotSame(caddy.bq, clone.bq);
        Assertions.assertNotEquals(caddy.bq, clone.bq);
        clone.bq.msg = "hi";
        Assertions.assertNotSame(caddy.bq, clone.bq);
        Assertions.assertNotEquals(caddy.bq, clone.bq);
        show(caddy, clone);
    }

    private static void show(BqShadowClone caddy, BqShadowClone clone) {
        logger.info("caddy={},clone={}", caddy, clone);
        logger.info("caddy.bq={},clone.bq={}", caddy.bq, clone.bq);
        logger.info("caddy.bq.msg={},clone.bq.msg={}", caddy.bq.msg, clone.bq.msg);
        logger.info("caddy.age={},clone.age={}", caddy.age, clone.age);
        logger.info("caddy.name={},clone.name={}", caddy.name, clone.name);
    }

    private static void show(BqDeepClone caddy, BqDeepClone clone) {
        logger.info("caddy={},clone={}", caddy, clone);
        logger.info("caddy.bq={},clone.bq={}", caddy.bq, clone.bq);
        logger.info("caddy.bq.msg={},clone.bq.msg={}", caddy.bq.msg, clone.bq.msg);
        logger.info("caddy.age={},clone.age={}", caddy.age, clone.age);
        logger.info("caddy.name={},clone.name={}", caddy.name, clone.name);
    }
}