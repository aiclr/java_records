package cn.aiclr.jvm.designpattern.structural.facade;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 模拟功能模块
 */
public class Model2 {
    private static final Logger log = LoggerFactory.getLogger(Model2.class);

    private Model2() {
    }

    private static final Model2 INSTANCE = new Model2();

    public static Model2 getInstance() {
        return INSTANCE;
    }

    public void play() {
        log.info("Model2 is PLAY");
        Model3.getInstance().on();
        Model3.getInstance().play();
    }

    public void start() {
        log.info("Model2 is START");
        play();
    }

    public void stop() {
        Model3.getInstance().off();
        log.info("Model2 is STOP");
    }


}
