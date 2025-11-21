package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Stack;

/**
 * <pre>归并排序 分治算法
 *
 * （先分，后治）==》栈---》递归
 *
 *  1,5,7,4,3,2
 *  1,5,7  4,3,2
 *  1,5  7  4,3  2
 *  1  5  7  4  3  2
 *  治
 *  1,5  7   3,4  2
 *  1,5,7    2,3,4
 *  1,2,3,4,5,6,7
 *
 *  分治算法
 *  把一个复杂问题分成两个或多个相同或相似的子问题，再把子问题分成更小的子问题，直到最后子问题可以简单的直接求解，
 *  原问题的解即为子问题的解的合并，
 *  快速排序，归并排序，傅立叶变换，快速傅立叶变换
 *
 *  经典问题
 *
 *  二分搜索
 *  大整数乘法
 *  棋盘覆盖
 *  合并排序
 *  快速排序
 *  线性时间选择
 *  最接近 点对 问题
 *  循环赛日程表
 *  汉诺塔
 *
 */
public class MergeSort {

    private static final Logger log = LoggerFactory.getLogger(MergeSort.class);

    /**
     * 自底向上（迭代）实现（非递归，节省栈空间）
     *
     */
    public static void asc(int[] arr) {
        if (arr == null || arr.length <= 1) return;

        int n = arr.length;
        int[] temp = new int[n];

        // 子数组大小从 1 开始，每次翻倍
        for (int size = 1; size < n; size *= 2) {
            // 对每一对相邻的 size 长度子数组进行合并
            for (int left = 0; left < n - size; left += 2 * size) {
                int mid = left + size - 1;
                int right = Math.min(left + 2 * size - 1, n - 1);
                merge(arr, temp, left, mid, right);
            }
        }
    }

    /**
     * 自顶向下（递归）实现（最常用）
     */
    public static void recursionAsc(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }
        int[] temp = new int[arr.length]; // 辅助数组，避免重复创建
        mergeSortRecursive(arr, temp, 0, arr.length - 1);
    }

    /**
     * 递归分治
     */
    private static void mergeSortRecursive(int[] arr, int[] temp, int left, int right) {
        if (left >= right) {
            return;
        }
        int mid = left + (right - left) / 2; // 防止溢出

        // 分：递归排序左右两半
        mergeSortRecursive(arr, temp, left, mid);
        mergeSortRecursive(arr, temp, mid + 1, right);

        // 治：合并两个有序子数组
        merge(arr, temp, left, mid, right);
    }

    /**
     * 合并 [left, mid] 和 [mid+1, right] 两个有序区间
     */
    private static void merge(int[] arr, int[] temp, int left, int mid, int right) {
        //拷贝数组
        // arr [left,right]
        // temp[left,right]
        System.arraycopy(arr, left, temp, left, right - left + 1);

        int i = left, j = mid + 1, k = left;
        //将 temp 临时数组内的数据排序后 放回 arr
        while (i <= mid && j <= right) {
            if (temp[i] <= temp[j]) {
                arr[k++] = temp[i++];
            } else {
                arr[k++] = temp[j++];
            }
        }
        //i 提前遍历完毕 剩余数组 [j,right]
        while (i <= mid) arr[k++] = temp[i++];
        //j 提前遍历完毕 剩余数组 [i,mid]
        while (j <= right) arr[k++] = temp[j++];
    }

    /**
     * <pre>汉诺塔:
     * 第一根棒上叠有 64 个从大到小的金盘，要求僧侣按规则将其移至第三根棒，每次仅能移动一个盘且大盘不可叠于小盘之上
     *
     * 分治
     *
     * 思路
     * 当盘子数大于1时，
     * 把所有盘子看成2个盘子，
     * 123 A B C
     *  3 为最大盘，
     *  12 为一个整体，
     * 步骤：
     * 1. 把 12 从 A 移到 B：hanoiTower(size - 1, A, C, B);
     *  1.0 相当于 12 A C B
     *  1.1 把 1 从 A 移到 C
     *  1.2 把 2 从 A 移到 B
     *  1.3 把 1 从 C 移到 B
     * 2. 把 3 从 A 移动到 C ：C.push(A.pop());
     * 3. 把 12 从 B 移动到 C：hanoiTower(size - 1, B, A, C);
     *  3.0 相当于 12 从 B 移到 C
     *  3.1 把 1 从 B 移到 A
     *  3.2 把 2 从 B 移到 C
     *  3.3 把 1 从 A 移到 C
     *
     * 当成两个盘子，移动逻辑始终都是A->B,A->C,B->C
     *
     * 将盘子层层递归分成两个盘子
     *
     * @param size 盘子数量
     * @param A 第一个柱子 先进后出--栈
     * @param B 第二个柱子 先进后出--栈
     * @param C 第三个柱子 先进后出--栈
     */
    public static void hanoiTower(int size, Stack<Integer> A, Stack<Integer> B, Stack<Integer> C) {
        if (size == 1) {
            C.push(A.pop());
        } else {
            hanoiTower(size - 1, A, C, B);
//            log.info("A={} B={} C={}", A, B, C);
            C.push(A.pop());
//            log.info("A={} B={} C={}", A, B, C);
            hanoiTower(size - 1, B, A, C);
//            log.info("A={} B={} C={}", A, B, C);
        }
    }
}
