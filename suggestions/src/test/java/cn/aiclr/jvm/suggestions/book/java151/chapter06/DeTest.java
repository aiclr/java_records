package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DeTest {

    @Test
    @DisplayName("枚举和接口常量检索对照")
    void testConstant() {
        De.describe(1);
        De.describe(De.SeasonDe.Winter);
    }

    @Test
    @DisplayName("枚举和接口常量遍历全部对照")
    void testShowAll() {
        De.SeasonDe.showALl();
        De.SeasonDeOfI.showALl();
    }
}
