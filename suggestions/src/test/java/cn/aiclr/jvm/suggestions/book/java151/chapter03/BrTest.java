package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import cn.aiclr.jvm.suggestions.utils.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BrTest {

    private static final Logger logger = LoggerFactory.getLogger(BrTest.class);

    @Test
    @DisplayName("序列化深拷贝")
    void serialDeepCopyTest() throws CloneNotSupportedException {
        Bq hello = new Bq("hello");
        Br caddy = new Br(128, "Caddy", hello);
        Br clone = SerializationUtils.clone(caddy);
        show(caddy, clone);

        Assertions.assertNotSame(caddy.age, clone.age);
        clone.age = 127;
        Assertions.assertNotSame(caddy.age, clone.age);
        show(caddy, clone);

        Assertions.assertNotSame(caddy.name, clone.name);
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

    @Test
    @DisplayName("apache 序列化工具类")
    void apacheUtils() throws CloneNotSupportedException {
        Bq hello = new Bq("hello");
        Br caddy = new Br(128, "Caddy", hello);
        byte[] bytes = org.apache.commons.lang3.SerializationUtils.serialize(caddy);
        Br clone = (Br) org.apache.commons.lang3.SerializationUtils.deserialize(bytes);
        show(caddy, clone);

        Assertions.assertNotSame(caddy.age, clone.age);
        clone.age = 127;
        Assertions.assertNotSame(caddy.age, clone.age);
        show(caddy, clone);

        Assertions.assertNotSame(caddy.name, clone.name);
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

    private static void show(Br caddy, Br clone) {
        logger.info("caddy={},clone={}", caddy, clone);
        logger.info("caddy.bq={},clone.bq={}", caddy.bq, clone.bq);
        logger.info("caddy.bq.msg={},clone.bq.msg={}", caddy.bq.msg, clone.bq.msg);
        logger.info("caddy.age={},clone.age={}", caddy.age, clone.age);
        logger.info("caddy.name={},clone.name={}", caddy.name, clone.name);
    }
}