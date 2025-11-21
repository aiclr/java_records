package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;
import java.util.Stack;

/**
 * <pre>快速排序，冒泡排序的改进
 * 通过一趟排序将要排序的数据分割成独立的两部分，
 * 其中一部分的所有数据都比另外一部分的所有数据都小
 * 然后再按照此方法对两部分数据分别进行快速排序，整个排序过程可以递归
 *
 * 时间复杂度
 * 平均情况：O(n log n)
 * 最坏情况：O(n²)（如每次选到最大/最小值为 pivot）
 * 最好情况：O(n log n)
 * 空间复杂度：O(log n)（递归栈）
 *
 * 优化建议
 *  1.随机化 pivot：避免最坏情况（已排序数组）。
 *  2.三数取中法：取首、中、尾三个数的中位数作为 pivot。
 *  3.小数组用插入排序：当子数组长度小于某个阈值（如10）时，改用插入排序。
 */

public class QuickSort {

    private static final Logger log = LoggerFactory.getLogger(QuickSort.class);

    /**
     * 基于栈非递归
     *
     * @param arr 待排序数组
     */
    public static void asc(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        quickSort(arr, 0, arr.length - 1, true);
    }

    /**
     * 基于栈非递归
     *
     * @param arr 待排序数组
     */
    public static void desc(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        quickSort(arr, 0, arr.length - 1, false);

    }

    public static void quickSort(int[] arr, int low, int high, boolean asc) {
        if (low >= high) return;

        // 创建一个栈来存储待处理的区间 [low, high]
        Stack<Integer> stack = new Stack<>();
        stack.push(low);
        stack.push(high);

        while (!stack.isEmpty()) {
            // 弹出当前处理的区间
            int h = stack.pop();
            int l = stack.pop();

            // 对 [l, h] 进行分区
            int pivotIndex = partition(arr, l, h, asc);

            // 如果 pivot 左边还有元素，压入栈
            if (pivotIndex - 1 > l) {
                stack.push(l);
                stack.push(pivotIndex - 1);
            }

            // 如果 pivot 右边还有元素，压入栈
            if (pivotIndex + 1 < h) {
                stack.push(pivotIndex + 1);
                stack.push(h);
            }
        }
    }

    /**
     * 递归
     *
     * @param arr 待排序数组
     */
    public static void recursionAsc(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        recursionQuickSort(arr, 0, arr.length - 1, true);
    }

    /**
     * 递归
     *
     * @param arr 待排序数组
     */
    public static void recursionDesc(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        recursionQuickSort(arr, 0, arr.length - 1, false);
    }

    public static void recursionQuickSort(int[] arr, int low, int high, boolean asc) {
        if (low < high) {
            // 获取分区索引
            int pivotIndex = partition(arr, low, high, asc);
            log.info("arr[{}]={} arr={}", pivotIndex, arr[pivotIndex], arr);
            // 递归排序左右子数组
            recursionQuickSort(arr, low, pivotIndex - 1, asc);
            recursionQuickSort(arr, pivotIndex + 1, high, asc);
        }
    }

    /**
     * <pre>三数取中法：取首、中、尾三个数的中位数作为 pivot。
     * 共六种排列
     * abc
     * cba
     * acb
     * bca
     * bac
     * cab
     *
     * @param arr 集合
     * @param low 最左
     * @param high 最右
     * @return 中位数索引
     */
    private static int partition(int[] arr, int low, int high, boolean asc) {
        // 取首、中、尾三个数的中位数作为 pivot。 并与最后一个元素交换
        int index;
        if ((arr[low] < arr[high / 2] && arr[high / 2] < arr[high]) || (arr[high] < arr[high / 2] && arr[high / 2] < arr[low])) {
            index = high / 2;
        } else if ((arr[high / 2] < arr[low] && arr[low] < arr[high]) || (arr[high] < arr[low] && arr[low] < arr[high / 2])) {
            index = low;
        } else {
            index = high;
        }
        //中位数与最后一个元素交换
        swap(arr, index, high);

        //中位数
        int pivot = arr[high];

        int i = low - 1;

        for (int j = low; j < high; j++) {
            if (asc) {
                //升序
                if (arr[j] <= pivot) {
                    //小于中位数 交换
                    i++;
                    swap(arr, i, j);
                }
            } else {
                //降序
                if (arr[j] >= pivot) {
                    //小于中位数 交换
                    i++;
                    swap(arr, i, j);
                }
            }

        }
        //大数部分第一位，与中位数交换
        swap(arr, i + 1, high);
        //返回中位数的索引
        return i + 1;
    }

    private static int partitionRandom(int[] arr, int low, int high) {
        // 随机选择 pivot 并与最后一个元素交换
        Random rand = new Random();
        int randomIndex = low + rand.nextInt(high - low + 1);
        swap(arr, randomIndex, high);

        int pivot = arr[high];
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high);
        return i + 1;
    }

    private static int partitionLast(int[] arr, int low, int high) {
        // 选择最后一个元素作为基准（pivot）
        int pivot = arr[high];
        int i = low - 1; // 小于 pivot 的元素的索引

        for (int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr, i, j);
            }
        }
        swap(arr, i + 1, high); // 将 pivot 放到正确位置
        return i + 1;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}
