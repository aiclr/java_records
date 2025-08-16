package cn.aiclr.jvm.suggestions.book.java151.chapter09.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre> 死锁
 * 线程死锁四个条件(只有满足了这些条件才可能产生线程死锁)
 *  1）互斥条件：一个资源每次只能被一个线程使用
 *  2）资源独占条件：一个线程因请求资源而阻塞时，对已获得的资源保持不放
 *  3）不剥夺条件：线程已获得的资源在未使用完之前，不能强行剥夺
 *  4）循环等待条件：若干线程之间形成一种头尾相接的循环等待资源关系（a=b,b=c,c=a）
 *
 * 解决线程死锁问题，就必须从这四个条件入手，一般情况下可以按照以下两种方式来解决：
 *  1)避免或减少资源共享
 *      一个资源被多个线程共享，若采用了同步机制，则产生的死锁可能性很大，
 *      特别是在项目比较庞大的情况下，很难杜绝死锁，
 *      对此最好的解决办法就是减少资源共享
 *      例如一个 B/S 结构的办公系统可以完全忽略资源共享，
 *      这是因为此类系统有三个特征：
 *          一是并发访问不会太高，
 *          二是读操作多于写操作，
 *          三是数据质量要求比较低，
 *       因此即使出现数据资源不同步的情况也不可能产生太大的影响，
 *       完全可以不使用同步技术。
 *       但是如果是一个支付清算系统就必须慎重考虑资源同步问题了，
 *       因为此类系统
 *          一是数据质量要求非常高（如果产生数据不同步的情况那可是重大生产事故），
 *          二是并发量大，
 *       不设置数据同步则会产生非常多的运算逻辑失效的情况，
 *       这会导致交易失败，产生大量的“脏”数据，
 *       系统可靠性将大大降低
 *  2)使用自旋锁。例子:
 *      线程 A 在等待线程 B 释放资源，而线程 B 又在等待线程 A 释放资源，僵持不下，
 *      如果线程 B 设置了超时时间,在等待 2 秒后还是无法获得资源，则自行终结该任务.
 */
public class DeadLock {

    /**
     * <pre>两个资源 a 和 b，在两个线程 A、B 中使用了该资源，
     * 由于两个资源之间有交互操作，并且都是同步方法，
     *
     * 线程 A 调用 a1 此时 线程 A 持有 a 对象锁,休眠 1 秒钟后，线程 A 访问资源 b 的 b2 方法 试图获取 b 对象锁，
     * 线程 B 调用 b1 此时 线程 B 持有 b 对象锁,休眠 1 秒钟后，线程 B 访问资源 a 的 a2 方法 试图获取 a 对象锁，
     * 此时
     * 线程 A 持有 a 对象锁，且等待 B 线程释放 b 对象锁
     * 线程 B 持有 b 对象锁，且等待 A 线程释放 a 对象锁
     *
     * 出现两个线程在互相等待释放资源的情况，也就是死锁
     *
     * 此种情况下，线程 A 和线程 B 会一直互等下去，直到有外界干扰为止，
     *      比如终止一个线程，
     *      或者某一线程自行放弃资源的争抢，
     * 否则这两个线程就始终处于死锁状态了
     */
    public static void main(String[] args) {

        DeadLockFooA a = new DeadLockFooA();
        DeadLockFooB b = new DeadLockFooB();

        new Thread(() -> a.a1(b), "线程A").start();
        new Thread(() -> b.b1(a), "线程B").start();
    }

    static class DeadLockFooA {

        private static final Logger logger = LoggerFactory.getLogger(DeadLockFooA.class);

        public synchronized void a1(DeadLockFooB b) {
            logger.info("{} _> DeadLockFooA#a1()", Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("{}", e.getMessage(), e);
            }
            logger.info("{} _> DeadLockFooB#b2()", Thread.currentThread().getName());
            b.b2();
        }

        public synchronized void a2() {
            logger.info("{} _> DeadLockFooA#a2()", Thread.currentThread().getName());
        }
    }

    static class DeadLockFooB {

        private static final Logger logger = LoggerFactory.getLogger(DeadLockFooB.class);

        public synchronized void b1(DeadLockFooA a) {
            logger.info("{} _> DeadLockFooB#b1()", Thread.currentThread().getName());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("{}", e.getMessage(), e);
            }
            logger.info("{} _> DeadLockFooA#a2()", Thread.currentThread().getName());
            a.a2();
        }


        public synchronized void b2() {
            logger.info("{} _> DeadLockFooB#b2()", Thread.currentThread().getName());
        }
    }
}
