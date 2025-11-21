package cn.aiclr.jvm.dsa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class MaxSubStringTest {

    @ParameterizedTest
    @CsvSource({
            "' ',0",
            "'   ',0",
            "'',0",
            "aa bc aaaa aaa,4",
            " aa bc aaaa aaa,4",
            "aa bc aaaa aaa aaa    aaa aa   aaaaa,5",
            "aa bc  aaaa aaa  ,4",
            "aa bc   aaaa aaa,4"})
    void getStrLength(String str, int expected) {
        Assertions.assertEquals(expected, MaxSubString.getStrLength(str));
    }

    @ParameterizedTest
    @CsvSource({
            "' ',0",
            "'   ',0",
            "'',0",
            "aa bc aaaa aaa,4",
            " aa bc aaaa aaa,4",
            "aa bc aaaa aaa aaa    aaa aa   aaaaa,5",
            "aa bc  aaaa aaa  ,4",
            "aa bc   aaaa aaa,4"})
    void getStrLengthPlus(String str, int expected) {
        Assertions.assertEquals(expected, MaxSubString.getStrLengthPlus(str));
    }

    @ParameterizedTest
    @CsvSource({
            "' ',1",
            "'   ',1",
            "'',0",
            "abcabcbb,3",
            "pwwkew,3",
            "aa bc   aaaa aaa,4"})
    void lengthOfLongestSubstring(String str, int expected) {
        Assertions.assertEquals(expected, MaxSubString.lengthOfLongestSubstring(str));
    }
}