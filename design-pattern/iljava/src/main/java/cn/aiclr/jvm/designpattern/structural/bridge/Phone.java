package cn.aiclr.jvm.designpattern.structural.bridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>高层实现类
 * 桥接模式
 */
public class Phone {
    protected static final Logger log = LoggerFactory.getLogger(Phone.class);

    private final Brand brand;

    public Phone(Brand brand) {
        this.brand = brand;
    }

    public void open() {
        this.brand.open();
    }

    public void close() {
        this.brand.close();
    }

    public void call() {
        this.brand.call();
    }
}
