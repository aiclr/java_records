package cn.aiclr.jvm.suggestions.book.java151.chapter09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <pre>130.使用 {@link java.util.concurrent.CountDownLatch} 协调子线程
 *
 * 案例：
 *      百米赛跑，多个参加赛跑的人员在听到发令枪响后，开始跑步，到达终点后结束计时，
 *      然后统计平均成绩。这里有两点需要考虑：
 *          一是发令枪响，这是所有跑步者（线程）接收到的出发信号，
 *              此处涉及裁判（主线程）如何通知跑步者（子线程）的问题；
 *          二是如何获知所有的跑步者完成了赛跑，
 *              也就是主线程如何知道子线程已经全部完成
 *
 * {@link java.util.concurrent.CountDownLatch} 类是一个倒数的同步计数器，
 * 在程序中启动了两个计数器：
 *      一个是开始计数器 begin，表示的是发令枪；
 *      另外是结束计数器 end，一共有 10 个，表示的是每个线程的执行情况，也就是跑步者是否跑完比赛。
 * 程序执行逻辑如下：
 *      1）10 个线程都开始运行，执行到begin 的 {@link java.util.concurrent.CountDownLatch#await()} 后线程阻塞，等待 begin 的计数变为 0。
 *      2）主线程调用 begin 的 {@link java.util.concurrent.CountDownLatch#countDown()} 方法，使 begin 的计数器为 0。
 *      3）10 个线程继续运行。
 *      4）主线程继续运行下一个语句，end 的计数器不为 0，主线程等待。
 *      5）每个线程运行结束时把 end 的计数器减 1，标志着本线程运行完毕。
 *      6）10 个线程全部结束，end 计数器为 0。
 *      7）主线程继续执行，打印出成绩平均值
 *
 * {@link java.util.concurrent.CountDownLatch} 的作用是控制一个计数器，
 * 每个线程在运行完毕后会执行 {@link java.util.concurrent.CountDownLatch#countDown()}，表示自己运行结束，
 * 这对于多个子任务的计算特别有效，
 * 比如一个异步任务需要拆分成 10 个子任务执行，
 * 主任务必须要知道子任务是否完成，
 * 所有子任务完成后才能进行合并计算，
 * 从而保证了一个主任务的逻辑正确性。
 * 这和我们的实际工作非常类似，
 *      比如领导安排了一个大任务给我，我一个人不可能完成，
 *      于是我把该任务分解给 10 个人做，
 *      在 10 个人全部完成后，我把这 10 个结果组合起来返回给领导—这就是 {@link java.util.concurrent.CountDownLatch} 的作用
 */
public class Ez {

    private static final Logger logger = LoggerFactory.getLogger(Ez.class);

    static class Runner implements Callable<Integer> {
        //开始信号
        private CountDownLatch begin;
        //结束信号
        private CountDownLatch end;

        public Runner(CountDownLatch begin, CountDownLatch end) {
            this.begin = begin;
            this.end = end;
        }

        @Override
        public Integer call() throws Exception {
            //跑步成绩
            int score = new Random().nextInt(25);
            //等待发令枪
            begin.await();
            //跑步中
            TimeUnit.MILLISECONDS.sleep(score);
            logger.info("{} _> {}", Thread.currentThread().getName(), score);
            //跑完全程
            end.countDown();
            return score;
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //参赛人数
        int num = 10;

        //发令枪只响一次
        CountDownLatch begin = new CountDownLatch(1);

        //参与跑步者有多个
        CountDownLatch end = new CountDownLatch(num);

        //每个跑步者一条跑道
        try (ExecutorService es = Executors.newFixedThreadPool(num)) {
            //记录比赛成绩
            List<Future<Integer>> futures = new ArrayList<>();

            //跑步者就位，所有线程处于等待状态
            for (int i = 0; i < num; i++) {
                futures.add(es.submit(new Runner(begin, end)));
            }

            //发令枪响，开始跑步
            begin.countDown();

            //等待所有跑步者跑完全程
            end.await();

            int count = 0;

            //统计总分
            for (Future<Integer> f : futures) {
                count += f.get();
            }

            logger.info("平均分数为：{} / {} = {}", count, num, count / num);
        }
    }
}