package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class VolatileForAtomic implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(VolatileForAtomic.class);

    private int i;

    @Override
    public void run() {
        try {
            TimeUnit.NANOSECONDS.sleep(100L);
        } catch (InterruptedException e) {
            logger.error("{}", e.getMessage(), e);
        }
        logger.info("{}", i++);
    }
}
