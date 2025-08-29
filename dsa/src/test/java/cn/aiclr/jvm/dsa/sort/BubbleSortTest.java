package cn.aiclr.jvm.dsa.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BubbleSortTest {

    @Test
    @DisplayName("冒泡排序 asc")
    void asc() {
        int[] a = {1, 8, 49, 50, 3, 10};
        BubbleSort.asc(a);
    }

    @Test
    @DisplayName("测试空数组")
    void testEmptyArray() {
        int[] arr = {};
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{}, arr); // 排序后仍为空数组

        arr = null;
        BubbleSort.asc(arr);
        assertArrayEquals(null, arr); // 排序后仍为空数组
    }

    @Test
    @DisplayName("测试已排序的数组（正序）")
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应保持不变
    }

    @Test
    @DisplayName("测试逆序数组")
    void testReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应变为正序
    }

    @Test
    @DisplayName("测试包含重复元素的数组")
    void testArrayWithDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, arr);
    }

    @Test
    @DisplayName("测试包含负数的数组")
    void testArrayWithNegativeNumbers() {
        int[] arr = {-3, 1, -4, 0, 2, -1};
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{-4, -3, -1, 0, 1, 2}, arr);
    }

    @Test
    @DisplayName("测试两个元素的数组（正序）")
    void testTwoElementsSorted() {
        int[] arr = {1, 2};
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
    }

    @Test
    @DisplayName("测试两个元素的数组（逆序）")
    void testTwoElementsReverse() {
        int[] arr = {2, 1};
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
    }

    @Test
    @DisplayName("测试大数组（性能和正确性，注意：冒泡排序在大数组上性能差，但逻辑应正确）")
    void testLargeArray() {
        int size = 100;
        int[] arr = new int[size];
        // 填充随机数（这里用简单模式：递减序列）
        for (int i = 0; i < size; i++) {
            arr[i] = size - i;
        }
        BubbleSort.asc(arr);
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
        BubbleSort.asc(arr);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, arr);
    }

}