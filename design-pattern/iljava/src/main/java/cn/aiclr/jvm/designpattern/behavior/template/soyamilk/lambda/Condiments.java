package cn.aiclr.jvm.designpattern.behavior.template.soyamilk.lambda;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Condiments {
    private static final Logger log = LoggerFactory.getLogger(Condiments.class);

    public void select() {
        log.info("选黄豆");
    }

    public void soak() {
        log.info("浸泡");
    }

    public void beat() {
        log.info("放入豆浆机打碎");
    }


    public void peanut() {
        log.info("加入花生");
    }

    public void redBean() {
        log.info("加入红豆");
    }
}
