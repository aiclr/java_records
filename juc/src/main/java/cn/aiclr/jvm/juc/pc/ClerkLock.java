package cn.aiclr.jvm.juc.pc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClerkLock {

    private static final Logger logger = LoggerFactory.getLogger(ClerkLock.class);

    //商品
    private int product = 0;

    Lock lock = new ReentrantLock();

    //线程间通信
    Condition condition = lock.newCondition();

    //进货
    public void get() {
        lock.lock();
        try {
            while (product >= 1) {
                logger.info("{}：满仓", Thread.currentThread().getName());
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    logger.error("{}", e.getMessage(), e);
                }
            }
            logger.info("{}:{}", Thread.currentThread().getName(), ++product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }

    //售货
    public void sale() {
        lock.lock();
        try {
            while (product <= 0) {
                logger.info("{}：缺货", Thread.currentThread().getName());
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    logger.error("{}", e.getMessage(), e);
                }
            }
            logger.info("{}:{}", Thread.currentThread().getName(), --product);
            condition.signalAll();
        } finally {
            lock.unlock();
        }

    }
}
