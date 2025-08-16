package cn.aiclr.jvm.suggestions.book.java151.chapter09.ex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FooSync {

    private static final Logger logger = LoggerFactory.getLogger(FooSync.class);

    public static void main(String[] args) {
        /**
         * m1 方法在执行时，main 线程持有 Foo 实例对象的锁，
         * 要想 t 线程获得 m2 方法的执行权限就必须等待 m1 方法执行完毕，
         * 也就是释放当前锁
         */
        Foo foo = new Foo();
        foo.m1();
        try (ExecutorService es = Executors.newSingleThreadExecutor()) {
            Future<?> future = es.submit(foo::m2);
            while (!future.isDone()) {
                logger.info("foo::m2 is running");
            }
            logger.info("foo::m2 is done");
        }
    }

    static class Foo {

        private static final Logger logger = LoggerFactory.getLogger(Foo.class);

        public synchronized void m1() {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                logger.error("{}", e.getMessage(), e);
            }
            logger.info("m1 执行完毕");
        }

        public synchronized void m2() {
            logger.info("m2 执行完毕");
        }
    }
}
