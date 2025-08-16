package cn.aiclr.jvm.suggestions.book.java151.chapter09.es;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>123.volatile 不能保证数据同步
 *
 * 注意：volatile 不能保证数据是同步的，只能保证线程能够获得最新值
 *
 * volatile 关键字比较少用，原因无外乎两点，
 * 一是在 Java 1.5 之前该关键字在不同的操作系统上有不同的表现，所带来的问题就是移植性较差；
 * 二是比较难设计，而且误用较多，这也导致它的“名誉”受损
 *
 * 每个线程都运行在栈内存中，
 * 每个线程都有自己的工作内存（Working Memory，比如寄存器 Register、高速缓冲存储器 Cache 等），
 * 线程的计算一般是通过工作内存进行交互的，
 * <a href="src/main/resources/img/123-1.svg">线程读取变量示意图: src/main/resources/img/123_1.png</a>
 * 线程在初始化时从主内存中加载所需的变量值到工作内存中，
 * 然后在线程运行时，
 *      如果是读取，则直接从工作内存中读取，
 *      若是写入则先写到工作内存中，之后再刷新到主存中，
 * 这是 JVM 的一个简单的内存模型，但是这样的结构在多线程的情况下有可能会出现问题，
 * 比如：
 *      A 线程修改变量的值，也刷新到了主存中，
 *      但 B、C 线程在此时间内读取的还是本线程的工作内存，
 *      也就是说它们读取的不是最“新鲜”的值，
 *      此时就出现了不同线程持有的公共资源不同步的情况
 * 使用 synchronized 同步代码块，
 * 或者使用 Lock 锁来解决该问题，
 * 不过，Java 可以使用 volatile 更简单地解决此类问题，
 * <a href="src/main/resources/img/123_2.png">volatile变量操作示意图: src/main/resources/img/123_2.png</a>
 * 比如在一个变量前加上 volatile 关键字，
 * 可以确保每个线程对本地变量的访问和修改都是直接与主内存交互的，
 * 而不是与本线程的工作内存交互的，保证每个线程都能获得最“新鲜”的变量值
 */
public class Es {

    private static final Logger logger = LoggerFactory.getLogger(Es.class);

    /**
     * <pre>
     * 1）启动 100 个线程，修改共享资源 count 的值
     * 2）暂停 15 毫秒，观察活动线程数是否为 1（即只剩下主线程在运行），若不为 1，则再等待 15 毫秒
     * 3）判断共享资源是否是不安全的，即实际值与理想值是否相同，若不相同，则发现目标，此时 count 的值为脏数据
     * 4）如果没有找到，继续循环，直到达到最大循环次数为止
     */
    public static void main(String[] args) throws Exception {
        //理想值，并作为最大循环次数
        int value = 1000;
        //循环次数，防止出现无线循环造成死机情况
        int loops = 0;
        //主线程组，用于估计活动线程数
        ThreadGroup tg = Thread.currentThread().getThreadGroup();
        while (loops++ < value) {
            //共享资源清零
            UnsafeThread ut = new UnsafeThread();
            for (int i = 0; i < value; i++) {
                new Thread(ut).start();
            }
            //先等15ms，等待活动线程数量为1 （idea run 运行 ）
            //先等15ms，等待活动线程数量为2 （idea debug 模式运行）
            do {
                Thread.sleep(15);
            } while (tg.activeCount() > 1);// idea run 运行
            //Thread[#1,main,5,main]
//            } while (tg.activeCount() > 2);// idea debug 模式运行
            //Thread[#1,main,5,main]
            //Thread[#23,IntelliJ Suspend Helper,5,main]

            //检查实际值于理论值是否一致
            if (ut.getCount() != value) {
                tg.list();
                logger.info("循环到第 {} 遍，出现线程不安全情况。此时，count={}", loops, ut.getCount());
                break;
            }
        }
    }
}
