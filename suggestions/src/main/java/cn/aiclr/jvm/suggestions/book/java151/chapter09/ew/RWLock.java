package cn.aiclr.jvm.suggestions.book.java151.chapter09.ew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 * 读写锁分离
 * 读读不互斥
 * 写写，读写互斥
 */
public class RWLock {

    private static final Logger logger = LoggerFactory.getLogger(RWLock.class);

    //读锁
    private final Lock rl;

    //写锁
    private final Lock wl;

    //mock 资源
    private Integer num;

    /**
     *
     * @param rwl 可重入的读写锁
     * @param num 资源
     */
    public RWLock(ReentrantReadWriteLock rwl, Integer num) {
        this.rl = rwl.readLock();
        this.wl = rwl.writeLock();
        this.num = num;
    }

    /**
     * 读操作，可并发执行
     */
    public void read() {
        rl.lock();
        try {
            logger.info("{}-{} read {}", System.nanoTime(), Thread.currentThread().getName(), num);
        } finally {
            rl.unlock();
        }
    }

    /**
     * 写操作，同时只允许一个写操作
     */
    public void write() {
        wl.lock();
        try {
            num++;
            logger.info("{},{} write {}", System.nanoTime(), Thread.currentThread().getName(), num);
        } finally {
            wl.unlock();
        }
    }

    public static void main(String[] args) {
        RWLock rwLock = new RWLock(new ReentrantReadWriteLock(), 0);
        try (ExecutorService res = Executors.newFixedThreadPool(3);
             ExecutorService wes = Executors.newFixedThreadPool(3)) {
            for (int i = 0; i < 5; i++) {
                res.submit(rwLock::read);
                wes.submit(rwLock::write);
            }
        }
    }
}
