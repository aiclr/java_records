package cn.aiclr.jvm.suggestions.book.java151.chapter09;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>118.不推荐覆写 {@link java.lang.Thread#start()}方法
 *
 * 继承自 {@link java.lang.Thread} 类的线程类不必覆写 {@link java.lang.Thread#start()} 方法
 *
 * 多线程比较简单的实现方式是继承 {@link java.lang.Thread} 类，
 * 然后覆写 {@link java.lang.Thread#run()} 方法，
 * 在客户端程序中通过调用对象的 {@link java.lang.Thread#start()} 方法即可启动一个线程，这是多线程程序的标准写法
 *
 * 覆写 {@link java.lang.Thread#start()} 的场景，都可以用其他的方式来实现，例如类变量、事件机制、监听等方式
 */
public class En extends Thread {

    private static final Logger logger = LoggerFactory.getLogger(En.class);

    public static void main(String[] args) {
        En en = new En();
        en.start();
    }

    @Override
    public void run() {
        logger.info("run...");
    }

    /**
     * <pre>非要覆写 {@link java.lang.Thread#start()} 方法，
     * 要在 {@link java.lang.Thread#start()}方法中加上 {@code super.start();}
     */
    @Override
    public void start() {
        /*线程启动前的业务处理*/
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("线程启动前的业务处理");

        super.start();

        /*线程启动后的业务处理*/
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        logger.info("线程启动后的业务处理");
    }
}
