package cn.aiclr.jvm.juc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WhileStronger implements Runnable {

    private static final Logger logger = LoggerFactory.getLogger(WhileStronger.class);

    private boolean flag = false;

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

