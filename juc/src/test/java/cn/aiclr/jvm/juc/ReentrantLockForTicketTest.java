package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

class ReentrantLockForTicketTest {

    private final static Logger logger = LoggerFactory.getLogger(ReentrantLockForTicketTest.class);

    @ValueSource(ints = {100})
    @ParameterizedTest
    @DisplayName("重入锁售票")
    void sellTicket(int ticketNum) {
        ReentrantLockForTicket ticket = new ReentrantLockForTicket(ticketNum);
        for (int i = 0; i < 3; i++) {
            new Thread(ticket, i + "_>").start();
        }
        try {
            //测试主线程暂停等待输出完整日志
            TimeUnit.MILLISECONDS.sleep(150L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @CsvSource({"3"})
    @ParameterizedTest
    @DisplayName("同步代码块售票")
    void sellTicketSyncBlock(int num) {
        Runnable ticket = new Runnable() {
            private int ticketNum = 100;

            @Override
            public void run() {
                try {
                    //增加延迟 提高锁竞争效果
                    TimeUnit.MILLISECONDS.sleep(20L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                while (ticketNum > 0) {
                    logger.info("{} 完成售票，余票为：{}", Thread.currentThread().getName(), sale());
                }
            }

            private int sale() {
                synchronized (this) {
                    return --ticketNum;
                }
            }
        };

        for (
                int i = 0;
                i < num; i++) {
            new Thread(ticket, i + "_>>").start();
        }

        try {
            //测试主线程暂停等待输出完整日志
            TimeUnit.MILLISECONDS.sleep(150L);
        } catch (
                InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @CsvSource({"3"})
    @ParameterizedTest
    @DisplayName("同步方法块售票")
    void sellTicketSyncMethod(int num) {
        Runnable ticket = new Runnable() {
            private volatile int ticketNum = 100;

            @Override
            public void run() {
                try {
                    //增加延迟 提高锁竞争效果
                    TimeUnit.MILLISECONDS.sleep(20L);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                while (ticketNum > 0) {
                    logger.info("{} 完成售票，余票为：{}", Thread.currentThread().getName(), sale());
                }
            }

            private synchronized int sale() {
                return --ticketNum;
            }
        };

        for (int i = 0; i < num; i++) {
            new Thread(ticket, i + "_>>>").start();
        }

        try {
            //测试主线程暂停等待输出完整日志
            TimeUnit.MILLISECONDS.sleep(150L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @CsvSource({"3,100"})
    @ParameterizedTest
    @DisplayName("无同步控制售票")
    void sellTicketNonSync(int num) {
        Runnable ticket = new Runnable() {
            private int ticketNum = 100;

            @Override
            public void run() {
                while (ticketNum > 0) {
                    logger.info("{} 完成售票，余票为：{}", Thread.currentThread().getName(), sale());
                }
            }

            private int sale() {
                return --ticketNum;
            }
        };

        for (int i = 0; i < num; i++) {
            new Thread(ticket, i + "_><_>").start();
        }

        try {
            //测试主线程暂停等待输出完整日志
            TimeUnit.MILLISECONDS.sleep(150L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }
}