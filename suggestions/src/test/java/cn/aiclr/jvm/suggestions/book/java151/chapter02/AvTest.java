package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

class AvTest {

    private static final Logger logger = LoggerFactory.getLogger(AvTest.class);

    @Test
    @DisplayName("不准确的浮点数 0.4")
    void test() {
        double res = 10.00 - 9.60;
        logger.info("10.00 - 9.06 = {}", res);
        Assertions.assertEquals(0.40000000000000036, res);
    }

    @Test
    @DisplayName("BigDecimal 精确计算浮点数")
    void testBigDecimal() {
        double d1 = 10.00;
        double d2 = 9.60;
        BigDecimal b1 = new BigDecimal(Double.toString(d1));
        BigDecimal b2 = new BigDecimal(Double.toString(d2));
        BigDecimal res = Av.cal(b1, b2, BigDecimal::subtract);
        logger.info("10.00 - 9.06 = {}", res);
    }

}