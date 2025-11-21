package cn.aiclr.jvm.dsa.sort;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("选择排序")
class SelectSortTest {
    @Test
    @DisplayName("asc")
    void asc() {
        int[] a = {1, 8, 49, 50, 3, 10};
        SelectSort.asc(a);
        assertArrayEquals(new int[]{1, 3, 8, 10, 49, 50}, a);
    }

    @Test
    @DisplayName("desc")
    void desc() {
        int[] a = {1, 8, 49, 50, 3, 10};
        SelectSort.desc(a);
        assertArrayEquals(new int[]{50, 49, 10, 8, 3, 1}, a);
    }

    @Test
    @DisplayName("测试空数组")
    void testEmptyArray() {
        int[] arr = {};
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{}, arr); // 排序后仍为空数组
        SelectSort.desc(arr);
        assertArrayEquals(new int[]{}, arr); // 排序后仍为空数组

        arr = null;
        SelectSort.asc(arr);
        assertArrayEquals(null, arr); // 排序后仍为空数组
        SelectSort.desc(arr);
        assertArrayEquals(null, arr); // 排序后仍为空数组
    }

    @Test
    @DisplayName("测试已排序的数组（正序）")
    void testAlreadySortedArray() {
        int[] arr = {1, 2, 3, 4, 5};
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应保持不变
        SelectSort.desc(arr);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, arr); // 应为倒叙
    }

    @Test
    @DisplayName("测试逆序数组")
    void testReverseSortedArray() {
        int[] arr = {5, 4, 3, 2, 1};
        SelectSort.desc(arr);
        assertArrayEquals(new int[]{5, 4, 3, 2, 1}, arr); // 应保持不变
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, arr); // 应变为正序
    }

    @Test
    @DisplayName("测试包含重复元素的数组")
    void testArrayWithDuplicates() {
        int[] arr = {3, 1, 4, 1, 5, 9, 2, 6, 5, 3};
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{1, 1, 2, 3, 3, 4, 5, 5, 6, 9}, arr);
        SelectSort.desc(arr);
        assertArrayEquals(new int[]{9, 6, 5, 5, 4, 3, 3, 2, 1, 1}, arr);
    }

    @Test
    @DisplayName("测试包含负数的数组")
    void testArrayWithNegativeNumbers() {
        int[] arr = {-3, 1, -4, 0, 2, -1};
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{-4, -3, -1, 0, 1, 2}, arr);
        SelectSort.desc(arr);
        assertArrayEquals(new int[]{2, 1, 0, -1, -3, -4}, arr);
    }

    @Test
    @DisplayName("测试两个元素的数组（正序）")
    void testTwoElementsSorted() {
        int[] arr = {1, 2};
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
        SelectSort.desc(arr);
        assertArrayEquals(new int[]{2, 1}, arr);
    }

    @Test
    @DisplayName("测试两个元素的数组（逆序）")
    void testTwoElementsReverse() {
        int[] arr = {2, 1};
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{1, 2}, arr);
        SelectSort.asc(arr);
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
        SelectSort.asc(arr);
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
    @DisplayName("测试大数组（性能和正确性）desc")
    void testDescLargeArray() {
        int size = 100;
        int[] arr = new int[size];
        // 填充随机数（这里用简单模式：递减序列）
        for (int i = 0; i < size; i++) {
            arr[i] = i+1;
        }
        SelectSort.desc(arr);
        // 检查是否为升序
        for (int i = 0; i < size - 1; i++) {
            assertTrue(arr[i] >= arr[i + 1]);
        }
        // 检查元素是否完整（简单检查首尾和总和）
        assertEquals(size, arr[0]);
        assertEquals(1, arr[size - 1]);
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
        SelectSort.asc(arr);
        assertArrayEquals(new int[]{Integer.MIN_VALUE, -1, 0, 1, Integer.MAX_VALUE}, arr);

        SelectSort.desc(arr);
        assertArrayEquals(new int[]{Integer.MAX_VALUE, 1, 0, -1, Integer.MIN_VALUE}, arr);
    }

}