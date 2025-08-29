package cn.aiclr.jvm.designpattern.structural;

import cn.aiclr.jvm.designpattern.structural.flyweight.FlyWeight;
import cn.aiclr.jvm.designpattern.structural.flyweight.FlyWeightManagement;
import cn.aiclr.jvm.designpattern.structural.flyweight.StateUnshared;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertSame;

@DisplayName("享元模式/蝇量模式")
class FlyWeightManagementTest {

    @Test
    void flyWeight() {
        FlyWeightManagement management = FlyWeightManagement.getInstance();
        FlyWeight flyWeight = management.get("嘿嘿");//new 实例
        FlyWeight flyWeight2 = management.get("嘿嘿");//从缓存获取 实例
        assertSame(flyWeight, flyWeight2);
        flyWeight.use(new StateUnshared("乾乾"));
        flyWeight2.use(new StateUnshared("坤坤"));
    }

    /**
     * <pre>查看 jdk 源码 {@link Integer#valueOf(int)}。
     *  当使用 {@link Integer#valueOf(int)} 声明 {@link Integer}时，
     *  如果值在 [-128,127] 范围内时，使用享元模式，返回的是同一个 Integer 对象，故 x==x1 为 true。
     *  [-128,127] 范围右边界可通过 jvm 参数配置 VM.getSavedProperty("java.lang.Integer.IntegerCache.high")。
     *  默认 Integer.IntegerCache.high=127
     *  {@code i >= -128 && i <= Integer.IntegerCache.high}
     *  Integer 对象保存在 Integer.IntegerCache.cache[]
     *  数组索引 index 与 Integer 对象对应
     */
    @Test
    @DisplayName("Integer.valueOf(int)")
    void testIntegerValueOf() {

        Integer x = Integer.valueOf(127);
        Integer x1 = Integer.valueOf(127);
        assertSame(x, x1);

        @SuppressWarnings("removal")
        Integer y = new Integer(127);

        @SuppressWarnings("removal")
        Integer y1 = new Integer(127);

        assertEquals(x, y);
        assertEquals(y, y1);

        assertNotSame(x, y);
        assertNotSame(x, y1);
        assertNotSame(y, y1);

        Integer x2 = Integer.valueOf(128);
        Integer x3 = Integer.valueOf(128);
        assertNotSame(x2, x3);
        assertEquals(x2, x3);
    }
}