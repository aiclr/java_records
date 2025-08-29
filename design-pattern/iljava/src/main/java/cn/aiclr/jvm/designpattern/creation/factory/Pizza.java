package cn.aiclr.jvm.designpattern.creation.factory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 产品
 */
public class Pizza {

    protected static final Logger log = LoggerFactory.getLogger(Pizza.class);

    String name;

    public void prepare() {
    }

    public void bake() {
        log.info("{} bake", name);
    }

    public void cut() {
        log.info("{} cut", name);
    }

    public void box() {
        log.info("{} box", name);
    }


    public void setName(String name) {
        this.name = name;
    }
}
