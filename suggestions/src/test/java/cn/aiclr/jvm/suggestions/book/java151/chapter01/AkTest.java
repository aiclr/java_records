package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import cn.aiclr.jvm.suggestions.utils.SerializationUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Execution(ExecutionMode.SAME_THREAD)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AkTest {

    private static final Logger logger = LoggerFactory.getLogger(AkTest.class);

    /**
     * 将java 对象序列化，保存到本地。
     * 将属性age相关屏蔽
     */
    @Test
    @Order(1)
    void writeObject() {
        Ak ak = new Ak();
        ak.setName("胜天半子");
        SerializationUtils.writeObject(ak);
    }

    /**
     * 读取java文件，反序了化java对象。
     * 将属性age相关 取消屏蔽
     */
    @Test
    @Order(2)
    void readObject() {
        Ak ak = (Ak) SerializationUtils.readObject();
        logger.info("{}", ak.getName());
        Assertions.assertEquals("胜天半子", ak.getName());
    }
}