package cn.aiclr.jvm.designpattern.structural.facade;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模拟功能模块
 */
public class Model3 {
    private static final Logger log = LoggerFactory.getLogger(Model3.class);

    private Model3() {
    }

    private static final Model3 INSTANCE = new Model3();

    public static Model3 getInstance() {
        return INSTANCE;
    }

    public void play() {
        log.info("Model3 is PLAY");
    }

    public void on() {
        log.info("Model3 is ON");
    }

    public void off() {
        log.info("Model3 is OFF");
    }

}
