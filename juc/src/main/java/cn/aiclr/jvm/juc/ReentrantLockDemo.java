package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 面试题 重入锁
 * 开启三个线程，线程ID分别为ABC，
 * 每个线程将自己的ID在屏幕上打印10遍，要求输出结果必须按顺序显示
 * ABCABCABC依次递归
 */
public class ReentrantLockDemo {

    private static final Logger logger = LoggerFactory.getLogger(ReentrantLockDemo.class);

    int num = 1;
    Lock lock = new ReentrantLock();
    Condition cA = lock.newCondition();
    Condition cB = lock.newCondition();
    Condition cC = lock.newCondition();

    StringBuffer sb = new StringBuffer();

    public void showA() {
        lock.lock();
        try {
            if (num != 1) {
                cA.await();
            }
            sb.append(Thread.currentThread().getName());
            logger.info("{}", sb);
            num = 2;
            cB.signal();
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } finally {
            lock.unlock();
        }

    }

    public void showB() {
        lock.lock();
        try {
            if (num != 2) {
                cB.await();
            }
            sb.append(Thread.currentThread().getName());
            logger.info("{}", sb);
            num = 3;
            cC.signal();
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    public void showC() {
        lock.lock();
        try {
            if (num != 3) {
                cC.await();
            }
            sb.append(Thread.currentThread().getName());
            logger.info("{}", sb);
            num = 1;
            cA.signal();
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }
}
