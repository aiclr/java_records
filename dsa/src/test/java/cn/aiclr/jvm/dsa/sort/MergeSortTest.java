package cn.aiclr.jvm.dsa.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@DisplayName("归并排序")
class MergeSortTest {

    private static final Logger log = LoggerFactory.getLogger(MergeSortTest.class);

    @DisplayName("汉诺塔Stack")
    @ParameterizedTest
    @CsvSource({"1","2","3","4"})
    void hanoiTower(int size) {
        Stack<Integer> A = new Stack<>();
        for (int i = size; i > 0; i--) {
            A.push(i);
        }
        Stack<Integer> B = new Stack<>();
        Stack<Integer> C = new Stack<>();
        MergeSort.hanoiTower(size, A, B, C);
        log.info("{}", C);
    }


    @Test
    @DisplayName("asc")
    void asc() {
        int[] a = {1, 8, 49, 50, 3, 10};
        MergeSort.asc(a);
        assertArrayEquals(new int[]{1, 3, 8, 10, 49, 50}, a);
    }

    @Test
    @DisplayName("测试空数组")
    void testEmptyArray() {
        int[] arr = {};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{}, arr); // 排序后仍为空数组

        arr = null;
        MergeSort.asc(arr);
        assertArrayEquals(null, arr); // 排序后仍为空数组
    }

    @Test
    @DisplayName("测试已排序的数组（正序）")
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应保持不变
    }

    @Test
    @DisplayName("测试逆序数组")
    void testReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应变为正序
    }

    @Test
    @DisplayName("测试包含重复元素的数组")
    void testArrayWithDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, arr);
    }

    @Test
    @DisplayName("测试包含负数的数组")
    void testArrayWithNegativeNumbers() {
        int[] arr = {-3, 1, -4, 0, 2, -1};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{-4, -3, -1, 0, 1, 2}, arr);
    }

    @Test
    @DisplayName("测试两个元素的数组（正序）")
    void testTwoElementsSorted() {
        int[] arr = {1, 2};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
    }

    @Test
    @DisplayName("测试两个元素的数组（逆序）")
    void testTwoElementsReverse() {
        int[] arr = {2, 1};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
    }

    @Test
    @DisplayName("测试大数组（性能和正确性）asc")
    void testAscLargeArray() {
        int size = 100;
        int[] arr = new int[size];
        // 填充随机数（这里用简单模式：递减序列）
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        MergeSort.asc(arr);
        // 检查是否为升序
        for (int i = 0; i < size - 1; i++) {
            assertTrue(arr[i] <= arr[i + 1]);
        }
        // 检查元素是否完整（简单检查首尾和总和）
        assertEquals(1, arr[0]);
        assertEquals(size, arr[size - 1]);
        // 计算期望总和 (1+2+...+100) = 100*101/2 = 5050
        long expectedSum = (long) size * (size + 1) / 2;
        long actualSum = 0;
        for (int num : arr) {
            actualSum += num;
        }
        assertEquals(expectedSum, actualSum);
    }

    @Test
    @DisplayName("边界测试：最大值和最小值")
    void testMaxMinValues() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 1, -1};
        MergeSort.asc(arr);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, arr);
    }


    @Test
    @DisplayName("asc Recursion")
    void ascRecursion() {
        int[] a = {1, 8, 49, 50, 3, 10};
        MergeSort.recursionAsc(a);
        assertArrayEquals(new int[]{1, 3, 8, 10, 49, 50}, a);
    }

    @Test
    @DisplayName("Recursion测试空数组")
    void testRecursionEmptyArray() {
        int[] arr = {};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{}, arr); // 排序后仍为空数组

        arr = null;
        MergeSort.recursionAsc(arr);
        assertArrayEquals(null, arr); // 排序后仍为空数组
    }

    @Test
    @DisplayName("Recursion测试已排序的数组（正序）")
    void testRecursionAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应保持不变
    }

    @Test
    @DisplayName("Recursion测试逆序数组")
    void testRecursionReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应变为正序
    }

    @Test
    @DisplayName("Recursion测试包含重复元素的数组")
    void testRecursionArrayWithDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, arr);
    }

    @Test
    @DisplayName("Recursion测试包含负数的数组")
    void testRecursionArrayWithNegativeNumbers() {
        int[] arr = {-3, 1, -4, 0, 2, -1};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{-4, -3, -1, 0, 1, 2}, arr);
    }

    @Test
    @DisplayName("Recursion测试两个元素的数组（正序）")
    void testRecursionTwoElementsSorted() {
        int[] arr = {1, 2};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
    }

    @Test
    @DisplayName("Recursion测试两个元素的数组（逆序）")
    void testRecursionTwoElementsReverse() {
        int[] arr = {2, 1};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
    }

    @Test
    @DisplayName("Recursion测试大数组（性能和正确性）asc")
    void testRecursionAscLargeArray() {
        int size = 100;
        int[] arr = new int[size];
        // 填充随机数（这里用简单模式：递减序列）
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        MergeSort.recursionAsc(arr);
        // 检查是否为升序
        for (int i = 0; i < size - 1; i++) {
            assertTrue(arr[i] <= arr[i + 1]);
        }
        // 检查元素是否完整（简单检查首尾和总和）
        assertEquals(1, arr[0]);
        assertEquals(size, arr[size - 1]);
        // 计算期望总和 (1+2+...+100) = 100*101/2 = 5050
        long expectedSum = (long) size * (size + 1) / 2;
        long actualSum = 0;
        for (int num : arr) {
            actualSum += num;
        }
        assertEquals(expectedSum, actualSum);
    }


    @Test
    @DisplayName("Recursion边界测试：最大值和最小值")
    void testRecursionMaxMinValues() {
        int[] arr = {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 1, -1};
        MergeSort.recursionAsc(arr);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, arr);
    }

}