package cn.aiclr.jvm.designpattern.structural.bridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 品牌类型 抽象
 */
public interface Brand {
    Logger log = LoggerFactory.getLogger(Brand.class);

    void open();

    void close();

    void call();
}
