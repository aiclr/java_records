package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * 闭锁：CountDownLatch
 * 在完成某些运算时，只有其他所有的运算全部完成时，当前运算才继续执行
 */
public class CountDownLatchDemo implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(CountDownLatchDemo.class);

    private CountDownLatch latch;

    @Override
    public void run() {
        synchronized (this) {
            try {
                for (int i = 0; i < 100; i++) {
                    if ((i & 1) == 0) {
                        //偶数
                        logger.info("{} {}", Thread.currentThread().getName(), i);
                    }
                }
            } finally {
                //CountDownLatch -1 直到减为 0
                latch.countDown();
            }
        }
    }

    public CountDownLatchDemo(CountDownLatch latch) {
        this.latch = latch;
    }
}
