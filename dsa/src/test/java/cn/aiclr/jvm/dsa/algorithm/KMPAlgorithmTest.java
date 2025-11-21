package cn.aiclr.jvm.dsa.algorithm;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

class KMPAlgorithmTest {

    static Stream<Arguments> kmpSearchData() {
        return Stream.of(
                Arguments.of("A", "A", 0),
                Arguments.of("AAB", "AB", 1),
                Arguments.of("ABABA", "ABA", 0),
                Arguments.of("BBC ABCDAB ABCDABCDABDE", "ABCDABD", 15),
                Arguments.of("ABCDABCDABCDFABCDABCDABCDE", "ABCDABCDABCDE", 13)
        );
    }

    static Stream<Arguments> kmpNextData() {
        return Stream.of(
                Arguments.of("A", new int[]{0}),
                Arguments.of("AB", new int[]{0, 0}),
                Arguments.of("ABA", new int[]{0, 0, 1}),
                Arguments.of("ABAB", new int[]{0, 0, 1, 2}),
                Arguments.of("ABCDABD", new int[]{0, 0, 0, 0, 1, 2, 0}),
                Arguments.of("ABCDABCDABCDE", new int[]{0, 0, 0, 0, 1, 2, 3, 4, 5, 6, 7, 8, 0})
        );
    }

    @ParameterizedTest
    @MethodSource("kmpSearchData")
    void kmpSearch(String str1, String str2, int index) {
        Assertions.assertEquals(index, KMPAlgorithm.kmpSearch(str1, str2, KMPAlgorithm.kmpNext(str2)));
    }

    @ParameterizedTest
    @MethodSource("kmpNextData")
    void kmpNext(String str, int[] next) {
        Assertions.assertArrayEquals(next, KMPAlgorithm.kmpNext(str));
    }
}