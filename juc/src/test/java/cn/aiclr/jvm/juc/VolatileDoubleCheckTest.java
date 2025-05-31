package cn.aiclr.jvm.juc;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

class VolatileDoubleCheckTest {

    private static final Logger logger = LoggerFactory.getLogger(VolatileDoubleCheckTest.class);

    @Test
    @DisplayName("while 竞争资源能力更强")
    @Timeout(1)
// 设置超时时间为1秒
    void whileTest() {
        WhileStronger task = new WhileStronger();
        //task 线程访问到 task.isFlag()=true
        new Thread(task, "task").start();
        while (true) {
            //while 竞争资源的能力更强，导致 task 线程修改后的值，迟迟无法提交到主存中，
            //即此时主存中值=false，test 线程值 false，task 线程值为true
            logger.info("-------{}", task.isFlag());
            if (task.isFlag()) {
                //test 线程访问到 task.isFlag()=false
                break;
            }
        }
    }

    @Test
    @DisplayName("while 竞争资源能力更强 加synchronized 同步锁")
    @Timeout(1)
// 设置超时时间为1秒
    void whileSyncTest() {
        WhileStronger task = new WhileStronger();
        //task 线程访问到 task.isFlag()=true
        new Thread(task, "task").start();
        while (true) {
            synchronized (task) {
                //while 竞争资源的能力更强，导致 task 线程修改后的值，迟迟无法提交到主存中，
                //即此时主存中值=false，test 线程值 false，task 线程值为true
                logger.info("-------{}", task.isFlag());
                if (task.isFlag()) {
                    //test 线程访问到 task.isFlag()=false
                    break;
                }
            }
        }
    }

    @Test
    @DisplayName("while 竞争资源能力更强 volatile")
    @Timeout(1)
// 设置超时时间为1秒
    void whileVolatileTest() {
        WhileVolatileStronger task = new WhileVolatileStronger();
        //task 线程访问到 task.isFlag()=true
        new Thread(task, "task").start();
        while (true) {
            synchronized (task) {
                //while 竞争资源的能力更强，导致 task 线程修改后的值，迟迟无法提交到主存中，
                //即此时主存中值=false，test 线程值 false，task 线程值为true
                logger.info("-------{}", task.isFlag());
                if (task.isFlag()) {
                    //test 线程访问到 task.isFlag()=false
                    break;
                }
            }
        }
    }

    @Test
    @DisplayName("原子性")
    void testVolatileAtomic() {
        //原子性
        VolatileForAtomic task = new VolatileForAtomic();
        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
        }
        try {
            TimeUnit.NANOSECONDS.sleep(1000L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }

    @Test
    @DisplayName("原子变量")
    void testAtomicInteger() {
        //原子变量
        VolatileInteger task = new VolatileInteger();
        logger.info("原子变量");
        for (int i = 0; i < 10; i++) {
            new Thread(task).start();
        }
        try {
            TimeUnit.NANOSECONDS.sleep(100L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
    }


}