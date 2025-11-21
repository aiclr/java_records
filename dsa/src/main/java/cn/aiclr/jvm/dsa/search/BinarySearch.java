package cn.aiclr.jvm.dsa.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre> 二分查找
 * 递归版
 *
 * 非递归版
 *
 * 如果 100 个数
 * 时间复杂度 = 以 2 为底 100 的对数
 * log   100     2^6< 100 <2^7,最慢7次就可以找到
 *     2
 *
 * 中值=（left+right）/2
 */
public class BinarySearch {

    /**
     * 递归版二分查找
     *
     * @param arr    有序数组
     * @param target 检索目标
     * @return 目标所在下标
     */
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        if (target >= arr[left] && target <= arr[right]) {
            int mid = left + (right - left) / 2;
            if (target == arr[mid]) {
                return mid;
            } else if (target > arr[mid]) {
                //右侧
                return binarySearch(target, arr, mid + 1, right);
            } else {
                //左侧
                return binarySearch(target, arr, left, mid - 1);
            }
        } else {
            return -1;
        }
    }

    /**
     * <pre>递归版二分查找
     */
    public static int binarySearch(int target, int[] arr, int left, int right) {
        if (left > right) {
            return -1;
        }
        int mid = left + (right - left) / 2;
        if (target == arr[mid]) {
            return mid;
        } else if (target > arr[mid]) {
            //右侧
            return binarySearch(target, arr, mid + 1, right);
        } else {
            //左侧
            return binarySearch(target, arr, left, mid - 1);
        }
    }

    /**
     * 查询重复值
     *
     * @param index 目标索引
     * @param arr   数组
     * @return 相同值的下标
     */
    public static List<Integer> findAll(int index, int[] arr) {
        List<Integer> result = new ArrayList<>();
        if (index >= 0) {
            int left = index - 1;
            while (left >= 0 && arr[index] == arr[left]) {
                result.addFirst(left);
                left--;
            }

            result.add(index);

            int right = index + 1;
            while (right < arr.length && arr[index] == arr[right]) {
                result.add(right);
                right++;
            }
        }
        return result;
    }

    /**
     * 非递归版二分查找算法
     *
     * @param arr    升序待查找数组 natural ordering ===1,2,3,4
     * @param target 查找目标
     * @return 查找目标的下标，-1表示没找到
     */
    public static int binarySearchNoRecur(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] > target) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return -1;
    }
}
