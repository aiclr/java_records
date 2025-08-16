package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;

class AyTest {

    private static final Logger logger = LoggerFactory.getLogger(AyTest.class);

    private static final BigDecimal val50 = new BigDecimal("10.5");
    private static final BigDecimal val51 = new BigDecimal("-10.5");

    private static final BigDecimal val40 = new BigDecimal("10.4");
    private static final BigDecimal val41 = new BigDecimal("-10.4");

    private static final BigDecimal val60 = new BigDecimal("10.6");
    private static final BigDecimal val61 = new BigDecimal("-10.6");

    private static final BigDecimal val0 = new BigDecimal("10.0");
    private static final BigDecimal val1 = new BigDecimal("-10.0");

    @Test
    void test() {
        // 四舍五入的经典案例，绝对值相同的两个数字，
        // 近似值为什么就不同了呢？这是由Math.round采用的舍入规则所决定的(采用的是正无穷方向舍入规则)
        long res1 = Math.round(10.5);
        long res2 = Math.round(-10.5);
        logger.info("{} != {}", res1, res2);
        Assertions.assertNotEquals(res1, res2);

        //四舍五入会导致损失
        int accountNum = 50000000;
        double cost = 0.005 * accountNum * 4;
        logger.info("四舍五入导致每年损失：{}", cost);

        //java5及以上版本，使用银行家舍入法减少金融系统损失
        BigDecimal money = new BigDecimal("888888");
        BigDecimal interestRate = new BigDecimal(Double.toString(0.0001 * 3));
        BigDecimal interest = money.multiply(interestRate).setScale(2, RoundingMode.HALF_EVEN);
        logger.info("{}元的季度利息={}", money, interest);

    }

    @Test
    @DisplayName("远离零方向舍入，此模式永远不会减小正数计算值，不会增大负数计算值")
    void testUP() {
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val50, 0, RoundingMode.UP));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val51, 0, RoundingMode.UP));
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val40, 0, RoundingMode.UP));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val41, 0, RoundingMode.UP));
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val60, 0, RoundingMode.UP));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val61, 0, RoundingMode.UP));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.UP));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.UP));
    }

    @Test
    @DisplayName("靠近零方向舍入（截断），此模式永远不会增大正数计算值，不会减小负数计算值")
    void testDOWN() {
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val50, 0, RoundingMode.DOWN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val51, 0, RoundingMode.DOWN));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val40, 0, RoundingMode.DOWN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val41, 0, RoundingMode.DOWN));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val60, 0, RoundingMode.DOWN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val61, 0, RoundingMode.DOWN));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.DOWN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.DOWN));
    }

    @Test
    @DisplayName("向正无穷大方向舍入，正数行为=UP，负数行为=DOWN，此模式永远不会减小计算值")
    void testCEILING() {
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val50, 0, RoundingMode.CEILING));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val51, 0, RoundingMode.CEILING));
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val40, 0, RoundingMode.CEILING));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val41, 0, RoundingMode.CEILING));
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val60, 0, RoundingMode.CEILING));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val61, 0, RoundingMode.CEILING));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.CEILING));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.CEILING));
    }

    @Test
    @DisplayName("向负无穷大方向舍入，正数行为=DOWN，负数行为=UP，此模式永远不会增大计算值")
    void testFLOOR() {
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val50, 0, RoundingMode.FLOOR));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val51, 0, RoundingMode.FLOOR));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val40, 0, RoundingMode.FLOOR));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val41, 0, RoundingMode.FLOOR));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val60, 0, RoundingMode.FLOOR));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val61, 0, RoundingMode.FLOOR));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.FLOOR));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.FLOOR));
    }

    @Test
    @DisplayName("四舍五入")
    void testHALF_UP() {
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val50, 0, RoundingMode.HALF_UP));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val51, 0, RoundingMode.HALF_UP));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val40, 0, RoundingMode.HALF_UP));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val41, 0, RoundingMode.HALF_UP));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.HALF_UP));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.HALF_UP));
    }

    @Test
    @DisplayName("五舍六入")
    void testHALF_DOWN() {
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val50, 0, RoundingMode.HALF_DOWN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val51, 0, RoundingMode.HALF_DOWN));
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val60, 0, RoundingMode.HALF_DOWN));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val61, 0, RoundingMode.HALF_DOWN));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.HALF_DOWN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.HALF_DOWN));
    }

    @Test
    @DisplayName("银行家舍入法，四舍六入五考虑，五后非零进一，五后为零看前一位奇偶，偶舍奇入")
    void testHALF_EVEN() {
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val50, 0, RoundingMode.HALF_EVEN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val51, 0, RoundingMode.HALF_EVEN));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val40, 0, RoundingMode.HALF_EVEN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val41, 0, RoundingMode.HALF_EVEN));
        Assertions.assertEquals(new BigDecimal("11"), Ay.scale(val60, 0, RoundingMode.HALF_EVEN));
        Assertions.assertEquals(new BigDecimal("-11"), Ay.scale(val61, 0, RoundingMode.HALF_EVEN));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.HALF_EVEN));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.HALF_EVEN));
    }

    @Test
    @DisplayName("不允许舍入，用于精确计算结果，如果计算值发生舍入，则抛出异常")
    void testUNNECESSARY() {
        Assertions.assertThrows(ArithmeticException.class, () -> Ay.scale(val50, 0, RoundingMode.UNNECESSARY));
        Assertions.assertThrows(ArithmeticException.class, () -> Ay.scale(val51, 0, RoundingMode.UNNECESSARY));
        Assertions.assertThrows(ArithmeticException.class, () -> Ay.scale(val40, 0, RoundingMode.UNNECESSARY));
        Assertions.assertThrows(ArithmeticException.class, () -> Ay.scale(val41, 0, RoundingMode.UNNECESSARY));
        Assertions.assertThrows(ArithmeticException.class, () -> Ay.scale(val60, 0, RoundingMode.UNNECESSARY));
        Assertions.assertThrows(ArithmeticException.class, () -> Ay.scale(val61, 0, RoundingMode.UNNECESSARY));
        Assertions.assertEquals(new BigDecimal("10"), Ay.scale(val0, 0, RoundingMode.UNNECESSARY));
        Assertions.assertEquals(new BigDecimal("-10"), Ay.scale(val1, 0, RoundingMode.UNNECESSARY));
    }
}