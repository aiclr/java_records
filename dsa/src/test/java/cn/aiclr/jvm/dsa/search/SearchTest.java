package cn.aiclr.jvm.dsa.search;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@DisplayName("查找算法")
class SearchTest {

    private static final Logger log = LoggerFactory.getLogger(SearchTest.class);

    @Test
    @DisplayName("二分查找")
    void testBinarySearch() {
        int[] arr = new int[]{1, 8, 10, 89, 1000, 1234};
        Assertions.assertEquals(-1, BinarySearch.binarySearch(arr, 0));
        Assertions.assertEquals(-1, BinarySearch.binarySearch(arr, 9));
        Assertions.assertEquals(-1, BinarySearch.binarySearch(arr, 1235));

        Assertions.assertEquals(3, BinarySearch.binarySearch(arr, 89));
        Assertions.assertEquals(1, BinarySearch.binarySearch(arr, 8));
        Assertions.assertEquals(4, BinarySearch.binarySearch(arr, 1000));

        arr = new int[]{1, 8, 10, 89, 90, 100, 1000, 1000, 1000, 1000, 1000};
        int index = BinarySearch.binarySearch(arr, 1000);
        log.info("{},{}", index, BinarySearch.findAll(index, arr));

        //非递归版
        arr = new int[]{1, 8, 10, 89, 1000, 1234};
        Assertions.assertEquals(-1, BinarySearch.binarySearchNoRecur(arr, 0));
        Assertions.assertEquals(-1, BinarySearch.binarySearchNoRecur(arr, 9));
        Assertions.assertEquals(-1, BinarySearch.binarySearchNoRecur(arr, 1235));

        Assertions.assertEquals(3, BinarySearch.binarySearchNoRecur(arr, 89));
        Assertions.assertEquals(1, BinarySearch.binarySearchNoRecur(arr, 8));
        Assertions.assertEquals(4, BinarySearch.binarySearchNoRecur(arr, 1000));

        arr = new int[]{1, 8, 10, 89, 90, 100, 1000, 1000, 1000, 1000, 1000};
        index = BinarySearch.binarySearchNoRecur(arr, 1000);
        log.info("{},{}", index, BinarySearch.findAll(index, arr));

        arr = new int[]{1000, 1000, 1000, 1000, 1000, 1000, 1000};
        Assertions.assertEquals(-1, BinarySearch.binarySearchNoRecur(arr, 999));
        Assertions.assertEquals(-1, BinarySearch.binarySearchNoRecur(arr, 1001));

        Assertions.assertEquals(3, BinarySearch.binarySearchNoRecur(arr, 1000));

        index = BinarySearch.binarySearchNoRecur(arr, 1000);
        log.info("{},{}", index, BinarySearch.findAll(index, arr));
    }

    @CsvSource({"0", "1", "2", "3", "4", "5"})
    @ParameterizedTest
    @DisplayName("根据数组长度获取斐波那契数列")
    void testFibonacci(int arrLength) {
        int[] result = FibonacciSearch.fib(arrLength);
        log.info("{}-{}", arrLength, result);
    }

    @Test
    @DisplayName("斐波那契查找")
    void testFibonacciSearch() {
        int[] arr = new int[]{1, 8, 10, 89, 1000, 1234};
        Assertions.assertEquals(-1, FibonacciSearch.fibSearch(arr, 0));
        System.err.println();
        Assertions.assertEquals(-1, FibonacciSearch.fibSearch(arr, 9));
        System.err.println();
        Assertions.assertEquals(-1, FibonacciSearch.fibSearch(arr, 1235));
        System.err.println();
        Assertions.assertEquals(0, FibonacciSearch.fibSearch(arr, 1));
        System.err.println();
        Assertions.assertEquals(5, FibonacciSearch.fibSearch(arr, 1234));
        System.err.println();
        Assertions.assertEquals(3, FibonacciSearch.fibSearch(arr, 89));
        System.err.println();
        arr = new int[]{1, 8, 10, 89, 90, 100, 1000};
        Assertions.assertEquals(4, FibonacciSearch.fibSearch(arr, 90));
        System.err.println();
        arr = new int[]{1000, 1000, 1000, 1000, 1000, 1000, 1000};
        Assertions.assertEquals(-1, FibonacciSearch.fibSearch(arr, 999));
        System.err.println();
        Assertions.assertEquals(-1, FibonacciSearch.fibSearch(arr, 1001));
        System.err.println();
        Assertions.assertEquals(4, FibonacciSearch.fibSearch(arr, 1000));
        System.err.println();
    }

    @Test
    @DisplayName("插值查找")
    void testInterpolationSearch() {
        int[] a = new int[100];
        for (int i = 0; i < 100; i++) {
            a[i] = i;
        }
        a[7] = 8;
        a[6] = 7;
        int target = InterpolationSearch.interpolationSearch(a, 7);
        Assertions.assertEquals(6, target);
        List<Integer> all = InterpolationSearch.findAll(target, a);
        log.info("{}", all);

        target = InterpolationSearch.interpolationSearch(a, 8);
        Assertions.assertEquals(8, target);
        all = InterpolationSearch.findAll(target, a);
        log.info("{}", all);

        a = new int[]{1, 8, 10, 89, 1000, 1000, 1234};
        target = InterpolationSearch.interpolationSearch(a, 1000);
        Assertions.assertEquals(4, target);
        all = InterpolationSearch.findAll(target, a);
        log.info("{}", all);
    }

}
