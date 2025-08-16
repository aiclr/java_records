package cn.aiclr.jvm.suggestions.book.java151.chapter03;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class BlTest {

    private static final Logger logger = LoggerFactory.getLogger(BlTest.class);

    @Test
    @DisplayName("静态内部类可以独立存在")
    void testStaticClass() {
        Bl.HomeBl homeBl = new Bl.HomeBl("广州", "021");
        Bl bl = new Bl("李四");
        bl.setHome(homeBl);
    }
}