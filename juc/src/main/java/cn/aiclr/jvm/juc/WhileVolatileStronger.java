package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhileVolatileStronger implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WhileVolatileStronger.class);

    //多线程修改flag值相当于直接修改主存中数据
    private volatile boolean flag = false;

    @Override
    public void run() {
        flag = true;
        logger.info("flag= {}", isFlag());
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}

