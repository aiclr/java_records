package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;

class CountDownLatchDemoTest {

    @Test
    void countDownLatch() {
        final CountDownLatch latch = new CountDownLatch(5);
        CountDownLatchDemo latchDemo = new CountDownLatchDemo(latch);
        for (int i = 0; i < 5; i++) {
            new Thread(latchDemo,i+"_>").start();
        }

        try {
            //等待线程执行完毕
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}