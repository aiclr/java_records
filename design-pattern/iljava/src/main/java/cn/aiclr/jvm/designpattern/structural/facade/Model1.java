package cn.aiclr.jvm.designpattern.structural.facade;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模拟功能模块
 */
public class Model1 {
    private static final Logger log = LoggerFactory.getLogger(Model1.class);

    private Model1() {
    }

    private static final Model1 INSTANCE = new Model1();

    public static Model1 getInstance() {
        return INSTANCE;
    }

    public void play() {
        log.info("Model1 is PLAY");
    }

    public void on() {
        log.info("Model1 is ON");
        play();
    }

    public void off() {
        log.info("Model1 is OFF");
    }

}
