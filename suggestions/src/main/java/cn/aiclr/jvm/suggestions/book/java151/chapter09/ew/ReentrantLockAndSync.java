package cn.aiclr.jvm.suggestions.book.java151.chapter09.ew;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockAndSync {

    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockAndSync.class);

    public static void main(String[] args) {
        ReentrantLockAndSync obj = new ReentrantLockAndSync();
        obj.sync();
        obj.lock();

        obj.test();
    }

    /**
     *
     */
    private void sync() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            AtomicInteger tickets = new AtomicInteger(10);
            for (int i = 0; i < 10; i++) {
                SyncTask syncTask = new SyncTask(tickets);
                executorService.submit(syncTask);
            }
        }
    }

    /**
     * Lock定义为多线程类的私有属性是起不到资源互斥作用的，除非是把Lock定义为所有线程的共享变量
     */
    private void lock() {
        try (ExecutorService executorService = Executors.newFixedThreadPool(3)) {
            AtomicInteger tickets = new AtomicInteger(10);
            Lock lock = new ReentrantLock();
            for (int i = 0; i < 10; i++) {
                ReentrantLockTask reentrantLockTask = new ReentrantLockTask(tickets, lock);
                executorService.submit(reentrantLockTask);
            }
        }
    }

    /**
     * <pre>线程名称Thread-0、Thread-1、Thread-2会逐个输出
     * 也就是一个线程在执行时，其他线程就处于等待状态。
     */
    private void test() {
        Lock lock = new ReentrantLock();
        AtomicReference<Integer> num = new AtomicReference<>(1);

        new Thread(() -> {
            lock.lock();
            try {
                while (num.get() == 1) {
                    logger.info("{}", Thread.currentThread().getName());
                    num.set(2);
                }
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                while (num.get() == 2) {
                    logger.info("{}", Thread.currentThread().getName());
                    num.set(3);
                }
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            lock.lock();
            try {
                while (num.get() == 3) {
                    logger.info("{}", Thread.currentThread().getName());
                    num.set(1);
                }
            } finally {
                lock.unlock();
            }
        }).start();
    }
}
