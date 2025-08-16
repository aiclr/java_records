package cn.aiclr.jvm.suggestions.book.java151.chapter02;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class AxTest {

    @ParameterizedTest
    @CsvSource({
            "-2147483648,false",
            "0,true",
            "1000,true",
            "1001,false",
            "2147483647,true"
    })
    void checkTest(int value, boolean expected) {
        Assertions.assertEquals(expected, Ax.check(value));
    }
}