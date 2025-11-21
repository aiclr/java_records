package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>选择排序
 * 寻找最小值下标，与第一位换位
 * 从剩余数据找最小值小标，与第二位换位
 * 依次类推
 */
public class SelectSort {

    private static final Logger log = LoggerFactory.getLogger(SelectSort.class);

    public static void asc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        for (int i = 0; i < a.length; i++) {
            int min = a[i];
            int minIndex = i;
            int j = i + 1;

            while (j < a.length) {
                if (min > a[j]) {
                    min = a[j];
                    minIndex = j;
                }
                j++;
            }
            if (minIndex != i) {
                a[minIndex] = a[i];
                a[i] = min;
                log.info("{}",a);
            }
        }
    }

    public static void desc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        for (int i = 0; i < a.length; i++) {
            int max = a[i];
            int maxIndex = i;
            int j = i + 1;

            while (j < a.length) {
                if (max < a[j]) {
                    max = a[j];
                    maxIndex = j;
                }
                j++;
            }
            if (maxIndex != i) {
                a[maxIndex] = a[i];
                a[i] = max;
                log.info("{}",a);
            }
        }
    }
}
