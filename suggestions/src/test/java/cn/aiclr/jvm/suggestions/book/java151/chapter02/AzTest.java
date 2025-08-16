package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

class AzTest {

    @Test
    void test() {
        List<Integer> list= Arrays.asList(1,2,null);
        Assertions.assertEquals(3,Az.func(list));
        Assertions.assertThrows(NullPointerException.class,()->Az.funcErr(list));
    }
}