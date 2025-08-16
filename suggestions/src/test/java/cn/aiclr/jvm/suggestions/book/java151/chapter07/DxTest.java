package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DxTest {

    @Test
    void test() {
        Assertions.assertDoesNotThrow(() -> Dx.class.getDeclaredMethod("publicMethod"));
        Assertions.assertDoesNotThrow(() -> Dx.class.getMethod("publicMethod"));
        Assertions.assertDoesNotThrow(() -> Dx.class.getDeclaredMethod("privateMethod"));
        Assertions.assertThrows(NoSuchMethodException.class, () -> Dx.class.getMethod("privateMethod"));
    }
}
