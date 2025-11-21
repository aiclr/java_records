package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>希尔排序，缩小增量排序
 * 把记录按下标的一定增量分组，对每组使用直接插入排序算法排序，
 * 随着增量逐渐减少，每组包含的关键词越来越多，
 * 当增量减至 1 时，
 * 整个文件恰被分成一组
 */
public class ShellSort {

    private static final Logger log = LoggerFactory.getLogger(ShellSort.class);

    public static void asc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        //移动法，效率高,插入排序
        //gap增量，逐步缩小增量
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < a.length; i++) {
                int index = i;//待插入位置下标
                int tmp = a[i];//待插入值
                if (a[index] < a[index - gap]) {
                    while (index >= gap && tmp < a[index - gap]) {
                        a[index] = a[index - gap];
                        index -= gap;
                    }
                    if (index != i) {
                        a[index] = tmp;
                        log.info("gap={} array={}", gap, a);
                    }
                }
            }
        }
    }

    public static void desc(int[] a) {
        if (a == null || a.length <= 1) {
            return; // 空数组或单元素数组无需排序
        }
        //移动法，效率高,插入排序
        //gap增量，逐步缩小增量
        for (int gap = a.length / 2; gap > 0; gap /= 2) {
            for (int i = gap; i < a.length; i++) {
                int index = i;//待插入位置下标
                int tmp = a[i];//待插入值
                if (a[index] > a[index - gap]) {
                    while (index >= gap && tmp > a[index - gap]) {
                        a[index] = a[index - gap];
                        index -= gap;
                    }
                    if (index != i) {
                        a[index] = tmp;
                        log.info("gap={} array={}", gap, a);
                    }
                }
            }
        }
    }
}
