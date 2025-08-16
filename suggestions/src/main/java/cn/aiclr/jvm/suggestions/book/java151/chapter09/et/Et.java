package cn.aiclr.jvm.suggestions.book.java151.chapter09.et;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * <pre>124.异步运算考虑使用 {@link java.util.concurrent.Callable} 接口
 *
 * 多线程应用有两种实现方式，
 * 一种是实现 {@link java.lang.Runnable} 接口，
 * 另一种是继承 {@link java.lang.Thread} 类，
 * 这两个方式都有缺点：
 *      run 方法没有返回值，不能抛出异常（这两个缺点归根到底是 {@link java.lang.Runnable} 接口的缺陷，{@link java.lang.Thread} 也是实现了 {@link java.lang.Runnable} 接口），
 *      如果需要知道一个线程的运行结果就需要用户自行设计，线程类自身也不能提供返回值和异常。
 *      但是从 Java 1.5 开始引入了一个新的接口 {@link java.util.concurrent.Callable}，
 *      它类似于 {@link java.lang.Runnable} 接口，实现它就可以实现多线程任务
 */
public class Et {

    private static final Logger logger = LoggerFactory.getLogger(Et.class);

    /**
     * <pre>{@link java.util.concurrent.Executors}是一个静态工具类，提供了异步执行器的创建能力，
     *      如单线程执行器 {@link java.util.concurrent.Executors#newSingleThreadExecutor()} 、
     *      固定线程数量的执行器 {@link java.util.concurrent.Executors#newFixedThreadPool(int)} 等，
     * 一般它是异步计算的入口类。
     * {@link java.util.concurrent.Future} 关注的是线程执行后的结果，
     *      比如有没有运行完毕，执行结果是多少等
     *
     * 好处:
     *  1)尽可能多地占用系统资源，提供快速运算
     *  2)可以监控线程执行的情况，比如是否执行完毕、是否有返回值、是否有异常等
     *  3)可以为用户提供更好的支持，比如例子中的运算进度等
     */
    public static void main(String[] args) {
        //生成一个单线程异步执行器
        logger.info("开始");
        try (ExecutorService es = Executors.newSingleThreadExecutor()) {
            Future<Integer> future = es.submit(new TaxCalculator(100));
            while (!future.isDone()) {
                TimeUnit.SECONDS.sleep(1);
                System.out.print("#");
            }
            es.shutdown();
            logger.info("计算完成，税金是：{} 元", future.get());
        } catch (ExecutionException | InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }
}