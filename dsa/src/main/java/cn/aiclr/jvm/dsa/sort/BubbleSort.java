package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>冒泡排序，
 * 前后两位逆序，则交换位置
 */
public class BubbleSort {

    private static final Logger logger = LoggerFactory.getLogger(BubbleSort.class);

    public static void asc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        int tmp;
        for (int i = 0; i < a.length - 1; i++) {
            //判断是否发生交换，无交换=已有序
            boolean swapped = false;
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] > a[j + 1]) {
                    swapped = true;
                    tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
//                logger.info("i={} j={} arr={}", i, j, a);
            }
            if (!swapped)
                break;
        }
    }

    public static void desc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        int tmp;
        for (int i = 0; i < a.length; i++) {
            //判断是否发生交换，无交换=已有序
            boolean swapped = false;
            for (int j = 0; j < a.length - i - 1; j++) {
                if (a[j] < a[j + 1]) {
                    swapped = false;
                    tmp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = tmp;
                }
//                logger.info("i={} j={} arr={}", i, j, a);
            }
            if (swapped)
                break;
        }
    }
}
