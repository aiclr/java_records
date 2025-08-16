package cn.aiclr.jvm.suggestions.book.java151.chapter09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * <pre>125.优先选择线程池
 *
 * 在 Java 1.5 之前，实现多线程编程比较麻烦，需要自己启动线程，并关注同步资源，防止出现线程死锁等问题，
 * 在 1.5 版之后引入了并行计算框架，大大简化了多线程开发。
 * 线程有五个状态：{@link java.lang.Thread.State}
 *      新建状态（{@link java.lang.Thread.State#NEW}）、
 *      可运行状态（{@link java.lang.Thread.State#RUNNABLE}，也叫做运行状态）、
 *      阻塞状态（{@link java.lang.Thread.State#BLOCKED}）、
 *      等待状态（{@link java.lang.Thread.State#WAITING}）、
 *      等待状态（{@link java.lang.Thread.State#TIMED_WAITING},Thread.sleep(1000)）、
 *      结束状态（{@link java.lang.Thread.State#TERMINATED}），
 * 线程的状态只能由 {@link java.lang.Thread.State#NEW} 转变为了 {@link java.lang.Thread.State#RUNNABLE} 后才可能被阻塞或等待，最后终结，不可能产生本末倒置的情况，
 * 比如想把一个 {@link java.lang.Thread.State#TERMINATED} 状态的线程转变为 {@link java.lang.Thread.State#NEW} 状态，则会出现异常
 *
 * 一个线程的运行时间分为三部分：
 *      T1 为线程启动时间，
 *      T2 为线程体的运行时间，
 *      T3 为线程销毁时间，
 * 如果一个线程不能被重复使用，
 * 每次创建一个线程都需要经过 启动、运行、销毁这三个过程，那么这势必会增大系统的响应时间
 * T2 是无法避免的，只有通过优化代码来实现降低运行时间。
 * T1 和 T3 都可以通过线程池（Thread Pool）来缩减时间，
 *      比如在容器（或系统）启动时，创建足够多的线程，
 *      当容器（或系统）需要时直接从线程池中获得线程，
 *      运算出结果，再把线程返回到线程池中--- {@link java.util.concurrent.ExecutorService} 就是实现了线程池的执行器，
 *
 * 使用线程池减少的是线程的创建和销毁时间，
 * 这对于多线程应用来说非常有帮助，
 *      比如我们最常用的 Servlet 容器，每次请求处理的都是一个线程，
 *      如果不采用线程池技术，每次请求都会重新创建一个线程，
 *      这会导致系统的性能负荷加大，响应效率下降，降低了系统的友好性
 */
public class Eu {

    private static final Logger logger = LoggerFactory.getLogger(Eu.class);

    public static void main(String[] args) throws InterruptedException {
        Eu eu = new Eu();
        try {
            eu.threadState();
        } catch (IllegalThreadStateException | InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
        eu.useThreadPool();
    }

    /**
     * 手动修改现在状态
     *
     * @throws InterruptedException
     */
    private void threadState() throws InterruptedException {
        //新建状态
        Thread t = new Thread(() -> logger.info("线程在运行..."));
        //运行
        t.start();
        //是否运行状态，若不是等待10ms
        while (!t.getState().equals(Thread.State.TERMINATED)) {
            TimeUnit.MILLISECONDS.sleep(10);
        }
        //直接由结束状态转变为运行状态
        //报 IllegalThreadStateException 异常，原因就是不能从结束状态直接转变为可运行状态
        t.start();
    }

    /**
     * <pre>线程池技术
     * 实现线程池的三个名词
     * 1）工作线程（Worker）
     *      线程池中的线程，只有两个状态：可运行状态和等待状态，在没有任务时它们处于等待状态，运行时可以循环地执行任务
     * 2）任务接口（Task）
     *      每个任务必须实现的接口，以供工作线程调度器调度，
     *      它主要规定了
     *          任务的入口、
     *          任务执行完的场景处理、
     *          任务的执行状态等。
     *      这里有两种类型的任务：
     *          有返回值（或异常）的 {@link java.util.concurrent.Callable} 接口任务
     *          无返回值并兼容旧版本的 {@link java.lang.Runnable} 接口任务
     * 3）任务队列（Wok Queue）
     *      也叫做工作队列，用于存放等待处理的任务，
     *      一般是 {@link java.util.concurrent.BlockingQueue} 的实现类，用来实现任务的排队处理
     *
     * 结合源码分析得出
     * 线程池的创建过程：
     *      创建一个阻塞队列以容纳任务，
     *      在第一次执行任务时创建足够多的线程（不超过许可线程数），并处理任务，
     *      之后每个工作线程自行从任务队列中获得任务，
     *      直到任务队列中的任务数量为0为止，
     *      此时，线程将处于等待状态，
     *      一旦有任务再加入到队列中，
     *      即唤醒工作线程进行处理，实现线程的可复用性
     * <code>
     *     public class Executors {
     *           public static ExecutorService newFixedThreadPool(int nThreads) {
     *           //生成一个最大为 nThreads 的线程池执行器
     *         return new ThreadPoolExecutor(nThreads, nThreads,
     *                                       0L, TimeUnit.MILLISECONDS,
     *                                       new LinkedBlockingQueue<Runnable>());
     *     }
     *     }
     * </code>
     * 线程池中的线程是在 {@link java.util.concurrent.AbstractExecutorService#submit(Runnable)} 第一次提交任务时建立的
     * 代码关键是 execute 方法,实现了三个职责
     *      1)创建足够多的工作线程数，数量不超过最大线程数量，并保持线程处于运行或等待状态
     *      2)把等待处理的任务放到任务队列中
     *      3)从任务队列中取出任务来执行
     * 关键是工作线程的创建，
     *      它也是通过 new Thread 方式创建的一个线程，
     *      只是它创建的并不是我们的任务线程（虽然我们的任务实现了 Runnable 接口，但它只是起一个标志性的作用），
     *      而是经过包装的 {@link java.util.concurrent.ThreadPoolExecutor.Worker} 线程
     * <code>
     *     public abstract class AbstractExecutorService implements ExecutorService {
     *         public Future<?> submit(Runnable task) {
     *              //检查任务是否为null
     *              if (task == null) throw new NullPointerException();
     *              //把Runnable任务包装成具有返回值的任务对象，不过此时并没有执行，只是包装
     *              RunnableFuture<Void> ftask = newTaskFor(task, null);
     *              //执行此任务
     *              execute(ftask);
     *              //返回任务预期执行结果
     *              return ftask;
     *          }
     *     }
     * </code>
     * {@link java.util.concurrent.ThreadPoolExecutor.Worker} 线程 简化示意代码
     * {@link java.util.concurrent.ThreadPoolExecutor#execute(Runnable)} 方法是通过 {@link java.util.concurrent.ThreadPoolExecutor.Worker} 类启动的一个工作线程，执行的是我们的第一个任务，
     * 然后该线程通过 {@link java.util.concurrent.ThreadPoolExecutor#getTask()} 方法从任务队列中获取任务，之后再继续执行，
     * 但问题是任务队列是一个 {@link java.util.concurrent.BlockingQueue} ，是阻塞式的，
     * 也就是说如果该队列元素为 0，则保持等待状态，直到有任务进入为止
     * <code>
     *     public class ThreadPoolExecutor extends AbstractExecutorService {
     *         private final class Worker extends AbstractQueuedSynchronizer implements Runnable
     *          {
     *                Worker(Runnable firstTask) {
     *                      setState(-1); // inhibit interrupts until runWorker
     *                      this.firstTask = firstTask;
     *                      this.thread = getThreadFactory().newThread(this);
     *               }
     *
     *               public void run() {
     *                  runWorker(this);
     *              }
     *
     *          }
     *
     *          private Runnable getTask() {
     *             //...
     *         }
     *     }
     * </code>
     * 查看 {@link java.util.concurrent.LinkedBlockingQueue#take()}方法
     * <code>
     *     public class LinkedBlockingQueue<E> extends AbstractQueue<E> implements BlockingQueue<E>, java.io.Serializable {
     *           private static final long serialVersionUID = -6903933977591709194L;
     *
     *           public E take() throws InterruptedException {
     *               final E x;
     *               final int c;
     *               final AtomicInteger count = this.count;
     *               final ReentrantLock takeLock = this.takeLock;
     *               takeLock.lockInterruptibly();
     *               try {
     *                   //如果队列中元素数量为0，则等待
     *                   while (count.get() == 0) {
     *                       notEmpty.await();
     *                   }
     *                   //等待状态结束，弹出头元素
     *                   x = dequeue();
     *                   c = count.getAndDecrement();
     *                   //如果队列数量还等于1个，唤醒其他线程
     *                   if (c > 1)
     *                       notEmpty.signal();
     *               } finally {
     *                   takeLock.unlock();
     *               }
     *               if (c == capacity)
     *                   signalNotFull();
     *               //返回头元素
     *               return x;
     *          }
     *     }
     * </code>
     */
    private void useThreadPool() {
        //先创建了一个包含两个线程的线程池，然后在线程池中多次运行线程体

        //2个线程的线程池
        try (ExecutorService es = Executors.newFixedThreadPool(2)) {
            //多次执行线程体
            for (int i = 0; i < 4; i++) {
                es.submit(() -> logger.info("{}", Thread.currentThread().getName()));
            }
            //关闭执行器
            es.shutdown();
        }
    }
}