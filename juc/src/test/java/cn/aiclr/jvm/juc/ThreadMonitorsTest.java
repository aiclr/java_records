package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
class ThreadMonitorsTest {

    private final static Logger logger = LoggerFactory.getLogger(ThreadMonitorsTest.class);

    private ThreadMonitors monitor;

    @BeforeEach
    @DisplayName("单元测试前 初始化对象")
    void beforeAll() {
        monitor = new ThreadMonitors();
    }

    //1.两个普通同步方法，两个线程，标准打印====one two
    // (使用同一个锁this=num，one会先抢到锁，two等待）
    @Test
    @DisplayName("普通同步方法,同一个锁 this")
    void test1() {
        new Thread(() -> monitor.one1()).start();
        new Thread(() -> monitor.two1()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    //2.新增Thread.sleep(3000)给getOne2(),依旧是打印===等待3秒打印 one two
    // (使用同一个锁this=num，one会先抢到锁，two等待one释放锁）
    @Test
    @DisplayName("普通同步方法,同一个锁 this")
    void test2() {
        new Thread(() -> monitor.one2()).start();
        new Thread(() -> monitor.two2()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    //3.新增普通非同步方法，与2一起，打印结果===three 等待3秒 one two
    // (使用同一个锁this=num，one会先抢到锁，two等待one释放锁，three不需要锁 不用等待one释放锁 直接执行)
    @Test
    @DisplayName("普通同步方法与非同步方法")
    void test3() {
        new Thread(() -> monitor.one2()).start();
        new Thread(() -> monitor.two2()).start();
        new Thread(() -> monitor.three3()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    //4.两个Num对象，两个普通同步方法，打印 === two 等待3秒（其实会小于3秒，多线程） one
    // (每个对象一个锁num和num4，各用各的，num4的two不会去竞争num的one的锁，即普通同步方法的锁是对象实例持有)
    @Test
    @DisplayName("两个对象、两个普通同步方法,不同锁 this")
    void test4() {
        ThreadMonitors monitor4 = new ThreadMonitors();
        new Thread(() -> monitor.one2()).start();
        new Thread(() -> monitor4.two2()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    //5.getOne5()为静态同步方法，getTwo5为普通同步方法，打印 === two 等待3秒 one
    // (非静态同步方法two的锁为num，静态同步方法one的锁Class实例)
    @Test
    @DisplayName("静态同步方法 锁是 Class 实例、普通同步方法锁是 this 对象")
    void test5() {
        new Thread(() -> monitor.one5()).start();
        new Thread(() -> monitor.two5()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    //6.getOne6()和getTwo6()为静态同步方法，打印 === 等待3秒 one two
    // (静态同步方法之间会使用同一把锁Class实例，静态方法one获取锁，静态方法two等待one释放锁)
    @Test
    @DisplayName("静态同步方法,同一个锁 Class 实例")
    void test6() {
        new Thread(() -> monitor.one6()).start();
        new Thread(() -> monitor.two6()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    //7.两个对象，一个调用非静态同步方法，一个调用静态同步方法，打印====two 等待3秒 one     (one静态方法锁Class实例，two非静态方法锁在num4对象里，不会竞争一把锁)
    @Test
    @DisplayName("两个对象，一个调用非静态同步方法，一个调用静态同步方法，不同锁")
    void test7() {
        ThreadMonitors monitor4 = new ThreadMonitors();
        new Thread(() -> monitor.one5()).start();
        new Thread(() -> monitor4.two5()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    //8.两个对象，两个静态同步方法，打印==== 等待3秒one two                            (静态同步方法之间会使用同一把锁Class实例，one获取锁，two等待one释放锁)
    @Test
    @DisplayName("两个对象，两个静态同步方法,同一个锁 Class 实例")
    void test8() {
        ThreadMonitors monitor4 = new ThreadMonitors();
        new Thread(() -> monitor.one6()).start();
        new Thread(() -> monitor4.two6()).start();
        try {
            TimeUnit.MILLISECONDS.sleep(500L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

}