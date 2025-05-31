package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

class CollectionsSyncTest {

    private static final Logger logger = LoggerFactory.getLogger(CollectionsSyncTest.class);

    @Test
    @DisplayName("CopyOnWriteArrayList")
    void copyOnWriteArrayListTest() throws InterruptedException {
        CopyOnWriteArrayListForString cwal = new CopyOnWriteArrayListForString();
        for (int i = 0; i < 10; i++) {
            new Thread(cwal).start();
        }
        Thread.sleep(50L);
        cwal.show();
    }

    @Test
    @DisplayName("Collections.synchronizedList 基本使用")
    void synchronizedListTest() throws InterruptedException {
        //Collections.synchronizedList将ArrayList内方法全部转换为同步方法
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                //已加锁
                list.add("DD");
            }).start();
        }
        logger.info("{} {}", Thread.currentThread().getName(), list);
    }

    @Test
    @DisplayName("手动同步 iterator 迭代过程")
    void iteratorTest() throws InterruptedException {
        //Collections.synchronizedList将ArrayList内方法全部转换为同步方法
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                StringBuilder sb = new StringBuilder();
                //已加锁
                list.add("DD");
                // 必须手动同步迭代过程
                synchronized (list) {
                    Iterator<String> it = list.iterator();
                    while (it.hasNext()) {
                        String item = it.next();
                        sb.append(item);
                        sb.append(" ");
                    }
                    sb.append("\n");
                    sb.append(Thread.currentThread().getName());
                    sb.append(" ");
                    sb.append(list);
                    logger.info("{}", sb);
                }
            }).start();
        }

        Thread.sleep(1000);
        logger.info("{} {}", Thread.currentThread().getName(), list);
    }

    @Test
    @DisplayName("手动同步 foreach 迭代过程")
    void foreachTest() throws InterruptedException {
        //Collections.synchronizedList将ArrayList内方法全部转换为同步方法
        List<String> list = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                StringBuilder sb = new StringBuilder();
                //已加锁
                list.add("DD");
                // 必须手动同步迭代过程
                synchronized (list) {
                    for (String item : list) {
                        sb.append(item);
                        sb.append(" ");
                    }
                    sb.append("\n");
                    sb.append(Thread.currentThread().getName());
                    sb.append(" ");
                    sb.append(list);
                    logger.info("{}", sb);
                }
            }).start();
        }
        Thread.sleep(1000);
        logger.info("{} {}", Thread.currentThread().getName(), list);
    }

    @Test
    @DisplayName("复合操作需要同步")
    void compositeTest() throws InterruptedException {
        //Collections.synchronizedList将ArrayList内方法全部转换为同步方法
        List<String> list = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < 10; i++) {
            new Thread(() -> {
                // 复合操作需要手动同步
                StringBuilder sb = new StringBuilder();
                synchronized (list) {
                    if (!list.contains("AA")) {
                        list.add("DD");//已加同步锁，但不是原子操作
                    }
                    sb.append(Thread.currentThread().getName());
                    sb.append(" ");
                    sb.append(list);
                    logger.info("{}", sb);
                }
            }).start();
        }
        Thread.sleep(1000);
        logger.info("{} {}", Thread.currentThread().getName(), list);
    }

}
