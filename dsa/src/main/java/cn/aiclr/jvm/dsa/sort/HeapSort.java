package cn.aiclr.jvm.dsa.sort;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>堆排序
 *
 * 利用完全二叉树的性质
 * 升序构建大顶堆（父结点大于左右子结点的二叉树）
 * 降序构建小顶堆（父结点小于左右子结点的二叉树）
 * 使用数组存放二叉树，root 节点 n=0 ,左子结点下标=2n+1、右子结点下标=2n+2
 *
 * 最后一个非叶子结点 = a.length/2-1
 *
 * 例如
 * 待排序数组{4,6,8,5,9}
 * 对于二叉树
 *    4
 *  6   8
 * 5 9
 *
 * 大顶堆--升序
 * 一步步将其构建为一个大顶堆，先处理倒数第一个非叶子结点 6
 *    4
 *  9   8
 * 5 6
 *
 *    9
 *  4  8
 * 5 6
 *
 * 最后得到大顶堆
 *   9
 *  6 8
 * 5 4
 *
 * 对于数组 = 96854
 * 将最大值 9 放到末尾 = 4685 9
 *
 * 然后使用大顶堆逻辑处理剩余4685
 *   4
 *  6 8
 * 5
 *
 *   8
 *  6 4
 * 5
 * 8645 == 564 8 ===》564 89
 *  5
 * 6 4
 *
 *  6
 * 5 4
 * 654 = 45 6===45 689
 *  4
 * 5
 *  5
 * 4
 * 54 == 4 5 === 4 5689===45689
 *
 * 小顶堆大致逻辑是将最小值放在 root 结点，
 * 再将最小值放置到数组末尾，依次类推，获取降序排列
 * 小顶堆--降序
 *    4
 *  5   8
 * 6 9
 * 45869==9586 4
 *   9
 *  5 8
 * 6
 *
 *   5
 *  9 8
 * 6
 *
 *   5
 *  6 8
 * 9
 * 5689 == 968 5 == 968 54
 * ...
 */
public class HeapSort {

    private static final Logger log = LoggerFactory.getLogger(HeapSort.class);

    public static void asc(int[] a) {
        if (a == null || a.length <= 1) return;
        int temp = 0;
        //a.length/2-1 = 最后一个非叶子结点
        //使所有父结点的子树都比父结点小
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            adjustHeapAsc(a, i, a.length);
        }

        //依次将最大值找到，并放到当前数组的末尾
        for (int j = a.length - 1; j > 0; j--) {
            //交换最大值
            temp = a[j];
            a[j] = a[0];
            a[0] = temp;
            log.info("大顶堆{}", a);
            //从跟结点开始，依次找出最大值，交换位置
            adjustHeapAsc(a, 0, j);
        }
        log.info("结果{}", a);
    }

    public static void desc(int[] a) {
        if (a == null || a.length <= 1) return;
        int temp = 0;
        //a.length/2-1=最后一个非叶子结点
        //使所有父结点的子树都比父结点小
        for (int i = a.length / 2 - 1; i >= 0; i--) {
            adjustHeapDesc(a, i, a.length);
        }


        //依次将最大值找到，并放到当前数组的末尾
        for (int j = a.length - 1; j > 0; j--) {
            //交换最大值
            temp = a[j];
            a[j] = a[0];
            a[0] = temp;
            log.info("小顶堆{}", a);
            //从跟结点开始，依次找出最大值，交换位置
            adjustHeapDesc(a, 0, j);
        }
        log.info("结果{}", a);
    }


    /**
     * <pre>将数组（二叉树），调整为一个大顶堆 升序
     *
     * @param a 数组
     * @param i 非叶子结点下标
     *          从最后一个非叶子结点开始 依次 -1 执行到跟结点后，该树的状态为：每个父结点的值都大于其子结点
     *          将最大值和末尾值交换位置，出现一个新的根结点，树结构没变，依旧是每个父结点的值都大于其子结点
     *          比较新的跟结点与所有子结点的大小，得到最大值，一次类推
     *          然后 i=0,由于是当成树，寻找最大值类似二分法，先比较左右子树哪个大，哪个大则往那一侧遍历，因为最大值最终要变成根结点
     *          所以要找出该侧的次大值，目的是保持树的结构-----每个父结点的值都大于其子结点
     *          然后再从跟结点执行该方法，每次执行完，会将最大值放到跟结点
     * @param length 表示对多少个元素进行调整，length逐渐减小
     */
    public static void adjustHeapAsc(int[] a, int i, int length) {
        log.info("{}", a);
        int temp = a[i];
        //i为非叶子结点，所以一定有子树
        for (int j = 2 * i + 1; j < length; j = j * 2 + 1) {
            //比较左右子树大小，右子树存在且比左子树大，则将j定位到大的值
            if (j + 1 < length && a[j] < a[j + 1]) {
                j++;
            }
            //比较当前结点和较大的子结点
            if (a[j] > temp) {
                a[i] = a[j];//最大值变为当前结点a[i]
                i = j;//循环比较
            } else {
                break;
            }
            log.info("{}", a);
        }
        //for过后，以i为父结点的树，变成了大顶堆树（局部）
        a[i] = temp;
        log.info("{}", a);
    }

    /**
     * 将数组（二叉树），调整为一个小顶堆 降 序
     *
     * @param a 数组
     */
    public static void adjustHeapDesc(int[] a, int i, int length) {

        log.info("{}", a);
        int temp = a[i];
        //i为非叶子结点，所以一定有子树
        for (int j = 2 * i + 1; j < length; j = j * 2 + 1) {
            //比较左右子树大小，右子树存在且比左子树大，则将j定位到大的值
            if (j + 1 < length && a[j] > a[j + 1]) {
                j++;
            }
            //比较当前结点和较大的子结点
            if (a[j] < temp) {
                a[i] = a[j];//最大值变为当前结点a[i]
                i = j;//循环比较
            } else {
                break;
            }
            log.info("{}", a);
        }
        //for过后，以i为父结点的树，变成了大顶堆树（局部）
        a[i] = temp;
        log.info("{}", a);
    }

}
