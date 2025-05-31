package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockForTicket implements Runnable {

    private final static Logger logger = LoggerFactory.getLogger(ReentrantLockForTicket.class);

    private int ticketNum;

    public ReentrantLockForTicket(int ticketNum) {
        this.ticketNum = ticketNum;
    }

    //公平锁
    Lock lock = new ReentrantLock(true);

    @Override
    public void run() {
        while (ticketNum > 0) {
            try {
                TimeUnit.NANOSECONDS.sleep(10L);
            } catch (InterruptedException e) {
                logger.error("{}", e.getMessage(), e);
            }
            if (lock.tryLock()) {
                try {
                    if (ticketNum > 0) {
                        logger.info("{} 完成售票，余票为：{}", Thread.currentThread().getName(), --ticketNum);
                    }
                } finally {
                    lock.unlock();
                }
            } else {
                logger.warn("{} 未抢到锁不能售票，余票为：{}", Thread.currentThread().getName(), ticketNum);
            }
        }
    }
}
