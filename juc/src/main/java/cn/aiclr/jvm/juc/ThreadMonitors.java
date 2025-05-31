package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;


/**
 * 线程8锁
 * <p>
 * 明确概念：java对象，实例对象 和 Class对象
 * 一个类对应一个Class对象，对应多个new 实例对象
 * 每一个类都有一个Class对象，每当编译一个新类就产生一个Class对象，
 * 每个类的运行时的类型信息就是用Class对象表示的。它包含了与类有关的信息。
 * 可以理解为：实例对象通过Class对象来创建
 * <p>
 * 1.两个普通同步方法，两个线程，标准打印====one two                                (使用同一个锁this=monitor，one会先抢到锁，two等待）
 * 2.新增Thread.sleep(3000)给one2(),依旧是打印===等待3秒打印 one two           (使用同一个锁this=monitor，one会先抢到锁，two等待one释放锁）
 * 3.新增普通非同步方法，与2一起，打印结果===three 等待3秒 one two                   (使用同一个锁this=monitor，one会先抢到锁，two等待one释放锁，three不需要锁 不用等待one释放锁 直接执行)
 * 4.两个ThreadMonitors对象，两个普通同步方法，打印 === two 等待3秒（其实会小于3秒，多线程） one    (每个对象一个锁monitor和monitor4，各用各的，monitor4的two不会去竞争monitor的one的锁，即普通同步方法的锁是对象实例持有)
 * 5.one5()为静态同步方法，two5为普通同步方法，打印 === two 等待3秒 one        (非静态同步方法two的锁为monitor，静态同步方法one的锁Class实例)
 * 6.one6()和two6()为静态同步方法，打印 === 等待3秒 one two                 (静态同步方法之间会使用同一把锁Class实例，静态方法one获取锁，静态方法two等待one释放锁)
 * 7.两个对象，一个调用非静态同步方法，一个调用静态同步方法，打印====two 等待3秒 one     (one静态方法锁Class实例，two非静态方法锁在monitor4对象里，不会竞争一把锁)
 * 8.两个对象，两个静态同步方法，打印==== 等待3秒one two                            (静态同步方法之间会使用同一把锁Class实例，one获取锁，two等待one释放锁)
 * <p>
 * 结论：
 * 某一时刻只有一个线程持有锁，无论几个方法
 * 非静态方法的锁默认为this
 * 静态方法的锁为对应的Class实例
 */
public class ThreadMonitors {

    private final static Logger logger = LoggerFactory.getLogger(ThreadMonitors.class);

    public synchronized void one1() {
        logger.info("one");
    }

    public synchronized void two1() {
        logger.info("two");
    }

    public synchronized void one2() {
        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
        logger.info("one");
    }

    public synchronized void two2() {
        logger.info("two");
    }

    public void three3() {
        logger.info("three");
    }

    public static synchronized void one5() {
        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
        logger.info("one");
    }

    public synchronized void two5() {
        logger.info("two");
    }

    public static synchronized void one6() {
        try {
            TimeUnit.MILLISECONDS.sleep(200L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
        logger.info("one");
    }

    public static synchronized void two6() {
        logger.info("two");
    }
}
