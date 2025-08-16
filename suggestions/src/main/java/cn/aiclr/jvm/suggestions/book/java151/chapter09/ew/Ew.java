package cn.aiclr.jvm.suggestions.book.java151.chapter09.ew;

/**
 * <pre>127.{@link java.util.concurrent.locks.Lock} 与 synchronized 是不一样的
 * 显式锁（{@link java.util.concurrent.locks.Lock}类）和内部锁（synchronized关键字）不同
 * 1）{@link java.util.concurrent.locks.Lock} 支持更细粒度的锁控制
 *      假设读写锁分离，写操作时不允许有读写操作存在，而读操作时读写可以并发执行，这一点内部锁就很难实现
 * 2）{@link java.util.concurrent.locks.Lock}是无阻塞锁，synchronized是阻塞锁
 *      当线程 A 持有锁时，线程 B 也期望获得锁，
 *      此时，如果程序中使用的是显式锁，
 *          则 B 线程为等待状态（在通常的描述中，也认为此线程被阻塞了），
 *      若使用的是内部锁则为
 *          阻塞状态
 * 3）{@link java.util.concurrent.locks.Lock} 可实现公平锁，synchronized 只能是非公平锁
 *      非公平锁：
 *          当一个线程 A 持有锁，而线程 B、C 处于阻塞（或等待）状态时，
 *          若线程 A 释放锁，JVM 将从线程 B、C 中随机选择一个线程持有锁并使其获得执行权，
 *          这叫做非公平锁（因为它抛弃了先来后到的顺序）；
 *      公平锁：
 *          若 JVM 选择了等待时间最长的一个线程持有锁，则为公平锁（保证每个线程的等待时间均衡）。
 *      注意：
 *          即使是公平锁，JVM 也无法准确做到“公平”，在程序中不能以此作为精确计算。
 *      显式锁默认是非公平锁，但可以在构造函数中加入参数 true 来声明出公平锁，
 *      而 synchronized 实现的是非公平锁，它不能实现公平锁
 * 4）{@link java.util.concurrent.locks.Lock} 是代码级的，synchronized 是 JVM 级的
 *      {@link java.util.concurrent.locks.Lock}是通过编码实现的，
 *      synchronized 是在运行期由 JVM 解释的，
 *      相对来说 synchronized 的优化可能性更高，毕竟是在最核心部分支持的，
 *      {@link java.util.concurrent.locks.Lock} 的优化则需要用户自行考虑
 *      显式锁和内部锁的功能各不相同，在性能上也稍有差别，
 *      但随着 JDK 的不断推进，相对来说，显式锁使用起来更加便利和强大，
 *      在实际开发中选择哪种类型的锁就需要根据实际情况考虑了：
 *      灵活、强大则选择 {@link java.util.concurrent.locks.Lock}，
 *      快捷、安全则选择 synchronized
 */
public class Ew {
}