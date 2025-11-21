package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * <pre>基数排序
 * 空间换时间
 * 负数不行
 */
public class RadixSort {

    private static final Logger log = LoggerFactory.getLogger(RadixSort.class);

    /**
     * view/Show Bytecode
     */
    public void postIncrement() {
        int t = 0;
        int a = t++; // a = 0, t = 1
    }

    /**
     * view/Show Bytecode
     */
    public void preIncrement() {
        int t = 0;
        int b = ++t; // b = 1, t = 1
    }

    /**
     * 显式 bucket 内存占用较大
     *
     * @param arr 待排数组
     */
    public static void radixSort(int[] arr) {
        if (arr == null) {
            return;
        }

        int length = arr.length;

        if (length <= 1) {
            return;
        }

        // 找到最大值，确定最大位数
        int max = Arrays.stream(arr).max().orElse(0);

        //桶，10个数组，
        int[][] bucket = new int[10][length];

        //记录每个桶内数据个数,count[0]=第一个桶内数据个数
        int[] count = new int[10];

        // 对每一位进行计数排序（从个位开始）
        for (int exp = 1; max / exp > 0; exp *= 10) {
            // exp = 1 个位，个位是几就放到
            // bucket[个位数][桶内个数]，
            // 桶内个数(count[digit]),每放一个就++;
            for (int k : arr) {
                int digit = k / exp % 10;
                bucket[digit][count[digit]] = k;
                count[digit]++;
            }

            int t = 0;

            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < count[i]; j++) {
                    // int t=0;
                    // int a = t++; 后置自增 a=0
                    //    ILOAD 1
                    //    IINC 1 1 //直接
                    //    ISTORE 2
                    //
                    // int t=0;
                    // int a = ++t; 前置自增 a=t=1
                    //    IINC 1 1
                    //    ILOAD 1
                    //    ISTORE 2
                    arr[t++] = bucket[i][j];
                }
                count[i] = 0;
            }
            log.info("{}", arr);
        }
    }


    /**
     * <pre>基数排序主方法 来自通义千问
     * 标准基数排序通常用“计数排序”代替显式 bucket，避免 bucket[radix][n] 的大内存开销
     *
     * @param arr 待排数组
     */
    public static void asc(int[] arr) {
        if (arr == null || arr.length <= 1) {
            return;
        }

        // 找到最大值，确定最大位数
        int max = Arrays.stream(arr).max().orElse(0);

        // 对每一位进行计数排序（从个位开始）
        for (int exp = 1; max / exp > 0; exp *= 10) {
            countingSortByDigit(arr, exp);
        }
    }

    // 对数组按指定的位（exp 表示 1, 10, 100...）进行计数排序
    private static void countingSortByDigit(int[] arr, int exp) {
        int n = arr.length;
        int[] output = new int[n]; // 输出数组
        int[] count = new int[10]; // 0~9 的计数数组

        // 统计当前位上每个数字（0-9）出现的次数
        for (int j : arr) {
            int digit = (j / exp) % 10;
            count[digit]++;
        }
        log.info("{}", count);

        // 将 count 转换为累积计数（即每个数字在输出中的结束位置）
        //  count[0] = 2
        // 个位为 0 的有两位，需要 output 两个位置，索引应为 0、1
        //  count[1] = 3
        // 个位为 1 的有三位，需要 output 三个位置，索引为 0、1、2
        // 但是其前有两位被个位为 0 的占据，所以索引应整体 +2 为 2、3、4，即 count[1]=count[1]+count[0]
        for (int i = 1; i < 10; i++) {
            count[i] += count[i - 1];
        }
        log.info("{}", count);

        // 从后往前遍历原数组，构建输出数组（保持稳定性）
        // 从后往前原因是升序排列，已排序数组的大数在后侧，需要先定位大数位置
        for (int i = n - 1; i >= 0; i--) {
            int digit = (arr[i] / exp) % 10;
            // count[digit] - 1 = 索引
            output[count[digit] - 1] = arr[i];
            //插入一位，则对应 count 的数量-1，用于计算索引
            count[digit]--;
        }
        log.info("{}", output);
        // 将排序结果复制回原数组
        System.arraycopy(output, 0, arr, 0, n);
    }

}
