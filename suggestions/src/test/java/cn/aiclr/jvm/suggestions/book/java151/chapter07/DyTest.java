package cn.aiclr.jvm.suggestions.book.java151.chapter07;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class DyTest {

    @Test
    @DisplayName("setAccessible 取消安全检查")
    void test() {
        try {
            Dy dy = new Dy();
            Method doStuff = dy.getClass().getDeclaredMethod("doStuff");
            if (!doStuff.canAccess(dy)) {
                //取消安全检查
                doStuff.setAccessible(true);
            }
            Assertions.assertEquals(123, doStuff.invoke(dy));

            Method doFinalStuff = dy.getClass().getDeclaredMethod("doFinalStuff");
            if (!doFinalStuff.canAccess(dy)) {
                //取消安全检查
                doFinalStuff.setAccessible(true);
            }
            Assertions.assertEquals("final doStuff", doFinalStuff.invoke(dy));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
