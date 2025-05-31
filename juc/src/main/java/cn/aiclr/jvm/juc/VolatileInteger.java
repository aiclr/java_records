package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class VolatileInteger implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VolatileInteger.class);

    private AtomicInteger i = new AtomicInteger();

    @Override
    public void run() {
        try {
            TimeUnit.NANOSECONDS.sleep(100L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
        logger.info("{}", i.getAndIncrement());
    }
}
