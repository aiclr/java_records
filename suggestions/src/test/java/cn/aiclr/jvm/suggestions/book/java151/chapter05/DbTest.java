package cn.aiclr.jvm.suggestions.book.java151.chapter05;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class DbTest {

    private static final Logger logger = LoggerFactory.getLogger(DbTest.class);

    @Test
    @DisplayName("ArrayList Fail-Fast: java.util.ConcurrentModificationException")
    void testArrayListModify() {
        List<String> tickets = new ArrayList<>();
        //初始化票据
        for (int i = 0; i < 10000; i++) {
            tickets.add("火车票" + i);
        }
        boolean exception = false;
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            //退票
            executorService.submit(() -> {
                while (tickets.size() < 10000) {
                    tickets.add("火车票" + new Random().nextInt());
                }
            }).get(800, TimeUnit.MILLISECONDS);

            //售票
            executorService.submit(() -> {
                for (String ticket : tickets) {
                    tickets.remove(ticket);
                }
            }).get(800, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            exception = true;
            Assertions.assertEquals("java.util.ConcurrentModificationException", e.getMessage());
        }
        Assertions.assertTrue(exception);
    }

    @Test
    @DisplayName("Vector Fail-Fast: java.util.ConcurrentModificationException")
    void testVectorModify() {
        Vector<String> tickets = new Vector<>();
        //初始化票据
        for (int i = 0; i < 10000; i++) {
            tickets.add("火车票" + i);
        }
        boolean exception = false;
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {
            //退票
            executorService.submit(() -> {
                while (tickets.size() < 10000) {
                    tickets.add("火车票" + new Random().nextInt());
                }
            }).get(800, TimeUnit.MILLISECONDS);

            //售票
            executorService.submit(() -> {
                for (String ticket : tickets) {
                    tickets.remove(ticket);
                }
            }).get(800, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            exception = true;
            Assertions.assertEquals("java.util.ConcurrentModificationException", e.getMessage());
        }
        Assertions.assertTrue(exception);
    }

    /**
     * <pre>线程不安全
     * 两个线程在卖同一张火车票，这才是线程不同步的问题，
     * {@link java.util.ArrayList} 方法虽然没有加 synchronized
     * 但是 {@link java.util.ArrayList#isEmpty()} 和 {@link java.util.ArrayList#removeFirst()} 是复合操作，额外加锁后同时只会允许一个线程进入操作，确保程序的可靠性
     */
    @Test
    @DisplayName("同步修改 ArrayList: java.util.ConcurrentModificationException")
    void testArrayListSync() {
        List<String> tickets = new ArrayList<>();
        //初始化票据
        for (int i = 0; i < 10; i++) {
            tickets.add("火车票" + i);
        }
        boolean exception = false;
        try (ExecutorService executorService = Executors.newFixedThreadPool(20)) {
            for (int i = 0; i < 20; i++) {
                //多线程售票
                executorService.submit(() -> {
                    synchronized (tickets) {
                        if (!tickets.isEmpty()) {
                            String ticket = tickets.removeFirst();
                            logger.info("售出【{}】", ticket);
                        } else {
                            logger.info("售罄");
                        }
                    }
                }).get(800, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            exception = true;
        }
        Assertions.assertFalse(exception);
    }

    /**
     * <pre>线程安全
     * 20线程在卖同一张火车票，这才是线程不同步的问题，
     * {@link java.util.Vector} 的每个方法前都加上了 synchronized 关键字，同时只会允许一个线程进入该方法，确保了程序的可靠性。
     * 但是 {@link java.util.Vector#isEmpty()} 和 {@link java.util.Vector#removeFirst()} 是复合操作，复合操作需要额外加锁！！！
     */
    @Test
    @DisplayName("同步修改 Vector: java.util.ConcurrentModificationException")
    void testVectorSync() {
        Vector<String> tickets = new Vector<>();
        //初始化票据
        for (int i = 0; i < 10; i++) {
            tickets.add("火车票" + i);
        }
        boolean exception = false;
        try (ExecutorService executorService = Executors.newFixedThreadPool(20)) {
            for (int i = 0; i < 20; i++) {
                //多线程售票
                executorService.submit(() -> {
                    synchronized (tickets) {
                        if (!tickets.isEmpty()) {
                            String ticket = tickets.removeFirst();
                            logger.info("售出【{}】", ticket);
                        } else {
                            logger.info("售罄");
                        }
                    }
                }).get(800, TimeUnit.MILLISECONDS);
            }
        } catch (Exception e) {
            exception = true;
        }
        Assertions.assertFalse(exception);
    }
}
