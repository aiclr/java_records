package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class DgTest {

    @Test
    @DisplayName("switch 空值异常")
    void testNPE() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Dg.doSports(null));
        Assertions.assertThrows(AssertionError.class, () -> Dg.doSportsOfOrdinal(null));
        Dg.doSports(Dg.SeasonDg.Summer);
        Dg.doSportsOfOrdinal(Dg.SeasonDg.Winter);
    }

}
