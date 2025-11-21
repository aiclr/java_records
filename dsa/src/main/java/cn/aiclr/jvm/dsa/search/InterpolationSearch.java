package cn.aiclr.jvm.dsa.search;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>插值查找（Interpolation Search）
 *
 * 核心思想：
 *  二分查找总是从中间位置开始查找；
 *  插值查找则根据目标值 “可能的位置” 来估算查找点，更贴近目标值的实际位置，尤其在数据均匀分布时效率极高。
 *
 * 数据较大，关键字分布较均匀（差值较小） 如果差值很离谱，不一定有二分法优秀
 *
 * 如果数据类似等差数列 {1,2,3,5,6,7,8,10}
 *
 * 使用二分法不是最优，二分法永远是一半一半。
 *
 * 可以根据差值 (target-a[left])/(a[right]-a[left])
 * 计算一下所查的值，在离哪端近一些,自适应
 *
 *
 * 二分法中值=（left+right)/2 = left+(right-left)/2
 *
 *
 *  {1, 8, 10, 89, 1000, 1000,1234};
 *  找 1000
 *
 *  差值查找
 *  mid = left + (right-left) * (target-a[left]) / (a[right]-a[left])
 *  mid = 0 + （6-0） * (1000-1)/(1234-1) = 6*999/1233 = 3996/1233 = 4 ===》1000 直接定位到
 *
 *  二分法 mid = (0+6) / 2 = 3 ===》89
 */
public class InterpolationSearch {

    /**
     * 插值查找
     *
     * @param arr    分布均匀的有序数组
     * @param target 目标
     * @return 目标下标
     */
    public static int interpolationSearch(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while (low <= high && target >= arr[low] && target <= arr[high]) {
            // 防止除零（所有元素相同）
            if (arr[low] == arr[high]) {
                if (arr[low] == target) return low;
                else return -1;
            }

            // 插值计算 mid
            int mid = low + ((target - arr[low]) * (high - low)) / (arr[high] - arr[low]);

            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                low = mid + 1;
            } else {
                high = mid - 1;
            }
        }
        return -1;
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

}
