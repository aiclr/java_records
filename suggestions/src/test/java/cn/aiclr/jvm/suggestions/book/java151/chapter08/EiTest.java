package cn.aiclr.jvm.suggestions.book.java151.chapter08;

import cn.aiclr.jvm.suggestions.book.java151.chapter08.ei.Ei;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class EiTest {

    @Test
    @DisplayName("finally return 覆盖返回值")
    void testCoverReturn() throws Exception {
        Assertions.assertNotEquals(100, Ei.doStuff(100));
        Assertions.assertEquals(-1, Ei.doStuff(100));

        Assertions.assertEquals(1, Ei.doStuff1());

        Assertions.assertEquals("王", Ei.doStuff2().getName());
    }

    @Test
    @DisplayName("finally return 屏蔽异常")
    void testShieldException() throws Exception {
        Assertions.assertDoesNotThrow(() -> Ei.doStuff(-1));
        Assertions.assertEquals(-1, Ei.doStuff(-1));
    }

}
