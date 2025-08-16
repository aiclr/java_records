package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DhTest {
    @Test
    @DisplayName("switch 枚举，建议 default 后直接抛出一个异常")
    void test() {
        Dh.logLevel(Dh.LogLevelDh.DEBUG);
        Assertions.assertThrows(IllegalArgumentException.class, () -> Dh.logLevel(Dh.LogLevelDh.FATAL));
    }
}
