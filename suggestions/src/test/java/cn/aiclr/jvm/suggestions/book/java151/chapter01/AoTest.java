package cn.aiclr.jvm.suggestions.book.java151.chapter01;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class AoTest {

    @Test
    void intToChinese() {

        assertEquals("壹", Ao.intToChinese(1));

        assertNotEquals("壹", Ao.intToChineseWithError(1));
        assertEquals("玖", Ao.intToChineseWithError(1));
    }
}