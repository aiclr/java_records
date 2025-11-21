package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>插入排序
 * 把 n 个待排序的元素看成一个有序表，一个无序表，
 * 开始时
 * 有序表中只包含 1 个元素
 * 无序表中有 n-1 个元素
 * 每次从无序表中取出第一个元素，把它的排序码依次与有序元素的排序码进行比较，
 * 将他插入到有序表中的适当位置，使之成为新的有序表
 */
public class InsertionSort {

    private static final Logger log = LoggerFactory.getLogger(InsertionSort.class);

    public static void asc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        int tmp;//插入值
        int index;//插入位置
        for (int i = 1; i < a.length; i++) {
            index = i - 1;
            tmp = a[i];
            while (index >= 0 && a[index] > tmp) {
                a[index + 1] = a[index];
                index--;
            }
            if (index + 1 != i) {
                a[index + 1] = tmp;
                log.info("{}", a);
            }
        }
    }

    public static void desc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        int tmp;//待插入值
        int index;//待插入位置
        for (int i = 1; i < a.length; i++) {
            tmp = a[i];
            index = i - 1;
            while (index >= 0 && a[index] < tmp) {
                a[index + 1] = a[index];
                index--;
            }
            if (index + 1 != i) {
                a[index + 1] = tmp;
                log.info("{}", a);
            }
        }
    }
}
