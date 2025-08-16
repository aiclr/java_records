package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import cn.aiclr.jvm.suggestions.book.java151.chapter07.ed.AbsPopulator;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ed.AbsPopulatorPro;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ed.UserPopulator;
import cn.aiclr.jvm.suggestions.book.java151.chapter07.ed.UserPopulatorPro;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EdTest {

    @Test
    @DisplayName("简单模板方法模式")
    void test() {
        AbsPopulator populator = new UserPopulator();
        populator.dataInitialing();
    }

    @Test
    @DisplayName("反射版模板方法模式")
    void testPro() {
        AbsPopulatorPro populator = new UserPopulatorPro();
        populator.dataInitialing();
    }
}
