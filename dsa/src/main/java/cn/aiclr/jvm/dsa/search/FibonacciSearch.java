package cn.aiclr.jvm.dsa.search;

import java.util.Arrays;

/**
 * <pre>斐波那契查找，
 * 黄金分割来分割数组，进行查找
 *
 * 利用斐波那契数列：F(0)=0, F(1)=1, F(k)=F(k-1)+F(k-2)
 * 找到最小的 k，使得 F(k) - 1 ≥ n（n 为数组长度）
 * 将原数组扩充至长度 F(k) - 1（不足部分用最后一个元素填充）
 * 初始划分点：mid = left + F(k-1) - 1
 * 根据比较结果：
 * arr[mid] > target → 搜索左段，k = k - 1
 * arr[mid] < target → 搜索右段，k = k - 2
 * 相等 → 返回索引
 */
public class FibonacciSearch {

    /**
     * 斐波那契查找算法
     *
     * @param arr    有序数组
     * @param target 查找的值
     * @return -1 = 未找到，
     */
    public static int fibSearch(int[] arr, int target) {
        int[] f = fib(arr.length);//斐波那契数列

        int left = 0;
        int right = arr.length - 1;

        int k = 0;//分割点下标
        //获取分割数值下标
        while (right > f[k] - 1) {
            k++;
        }

        //right = 5,f[k] = 8,8 > 5,需要扩充数组长度到8,k=5
        int[] temp = Arrays.copyOf(arr, f[k]);
        //需要使用a最后的数填充，
        //{1,8,10,89,1000,1234};===>{1,8,10,89,1000,1234,1234,1234};
        for (int i = right + 1; i < f[k]; i++) {
            temp[i] = arr[right];
        }


        int mid;//存放分割位置
        while (left <= right) {
            /*
             * mid 计算公式
             * 使用斐波那契数，对数组进行分割
             * 要求数组长度必须为斐波那契数，不足则用最后一位来补充
             *
             * f[k] 的黄金比数是 f[k-1]
             * 比如：8 的黄金比数是 5,
             *
             * 数组下标是从 0 开始，所以要将黄金比数 -1 ,即 f[k-1]-1
             * low + f[k-1]-1 = 黄金点下标
             *
             * 前半部分数组个数是 f[k-1]
             * 所以如果继续遍历前半部分，将 k-=1
             *
             * 后半部分数组个数是 f[k-2]
             * 如果继续遍历后半部分，将 k-=2
             *
             * 例如：
             * 代码内数组有 6 个，不是黄金分割数
             * 所以补充到 8 个
             * f[k] = 8, 8 的黄金数是 f[k-1]=5
             * 所以分割点是第五位，但是考虑到数组下标是从0开始，所以要 -1,即：mid 下标 = f[k-1]-1 = 5-1 = 4
             * 如果往左:(左边有 f[k-1] = 5 个数）5 的黄金数 = 3,f[k-1]-1 = 3-1 = 2 left 不变 =0,mid= 2
             * 如果往右:(右边有 f[k-2] = 3 个数）3 的黄金数 = 2,f[k-1]-1 = 2-1 = 1 left = 上一个 mid 值 4 + 1 = 5,新 mid = left + f[k-1]-1 = 5 + 1= 6
             *
             * {1,8,10,89,[1000],1234,1234,1234}
             * {1,8,[10],89,1000}                  {1234,[1234],1234}
             * {1,[8],10}                          {89,1000}
             * {[1],8}                             {10}
             */
            mid = left + f[k - 1] - 1;
            System.err.printf("mid=%d ", mid);
            //左边
            if (target < temp[mid]) {
                right = mid - 1;
                //f[k]=f[k-1]+f[k-2]
                //全部元素=前部分+后部分
                //前部分=f[(k-1)]=f[(k-1)-1]+f[(k-1)-2]
                k -= 1;
            } else if (target > temp[mid]) {//往右
                left = mid + 1;
                //f[k]=f[k-1]+f[k-2]
                //全部元素=前部分+后部分
                //后部分=f[(k-2)]=f[(k-2)-1]+f[(k-2)-2]
                k -= 2;
            } else {
                return Math.min(mid, right);
            }
        }
        return -1;
    }


    /**
     * 斐波那契数列：F(0)=1, F(1)=1, F(k)=F(k-1)+F(k-2)
     *
     * @param arrLength 数组长度 根据此数组长度计算所需的斐波那契数列
     * @return 斐波那契数列 {1,1,2,3,5,8,13,21,34}
     */
    public static int[] fib(int arrLength) {
        if (arrLength < 2) {
            return new int[]{1, 1};
        } else {
            //计算斐波那契数列长度 k
            int t1 = 1;
            int t2 = 1;
            int k = 2;
            boolean loop = true;
            while (loop) {
                loop = t1 + t2 <= arrLength;

                int tmp = t2;
                t2 = t1 + t2;
                t1 = tmp;
                k++;
            }
            //生成斐波那契数列
            int[] f = new int[k];
            f[0] = 1;
            f[1] = 1;
            for (int i = 2; i < k; i++) {
                f[i] = f[i - 1] + f[i - 2];
            }
            return f;
        }
    }
}
