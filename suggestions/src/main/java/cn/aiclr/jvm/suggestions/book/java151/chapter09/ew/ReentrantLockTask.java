package cn.aiclr.jvm.suggestions.book.java151.chapter09.ew;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;

/**
 * 显示锁任务
 */
public class ReentrantLockTask extends Task implements Runnable {

    //声明显式锁，但是不要在多线程类内部实例化，否则起不到资源互斥作用的
    private final Lock lock;

    public ReentrantLockTask(AtomicInteger tickets, Lock lock) {
        super(tickets);
        this.lock = lock;
    }

    /**
     * <pre>显式锁的锁定和释放必须在一个 try-finally 块中，
     * 确保即使出现运行期异常也能正常释放锁，保证其他线程能够顺利执行
     */
    @Override
    public void run() {
        //开始锁定
        lock.lock();
        try {
            doSth();
        } finally {
            //释放锁
            lock.unlock();
        }
    }
}
