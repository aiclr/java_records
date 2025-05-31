package cn.aiclr.jvm.juc;

import java.util.concurrent.RecursiveTask;

/**
 * 多线程+分治思想
 * <p>
 * Fork/Join框架
 * 与线程池区别
 * 1. 底层使用 工作窃取模式 work-stealing
 * 当执行新的任务时，它可以将其拆分成更小的任务执行，并将小任务加到线程队列中，fork，然后再从一个随机线程的队列中偷一个并把它放在自己的队列中
 * 2. 相对于一般的线程池实现，fork/join框架的优势体现再对其中包含的任务的处理方式上
 * 在一般的线程池中，如果一个线程正在执行的任务由于某些原因无法继续运行，那么该线程会处于等待状态
 * 而在fork/join框架实现中，如果某个子问题由于等待另一个子问题的完成而无法继续运行那么处理该子问题的线程会主动寻找其他尚未运行的子问题来执行
 * 这种方式减少了线程的等待时间，提高性能
 */
public class ForkJoinSmnCalculate extends RecursiveTask<Long> {

    private static final long serialVersionUID = 1L;

    private long start;

    private long end;

    //临界值
    private static final long THURSHOLD = 10000L;

    public ForkJoinSmnCalculate(long start, long end) {
        this.start = start;
        this.end = end;
    }

    @Override
    protected Long compute() {
        long length = end - start;
        if (length <= THURSHOLD) {
            long sum = 0L;
            for (long i = start; i <= end; i++) {
                sum += i;
            }
            return sum;
        } else {
            long mid = (start + end) / 2;
            ForkJoinSmnCalculate left = new ForkJoinSmnCalculate(start, mid);
            //分，并压入线程队列
            left.fork();
            ForkJoinSmnCalculate right = new ForkJoinSmnCalculate(mid + 1, end);
            //分，并压入线程队列
            right.fork();
            return left.join() + right.join();
        }
    }

}
