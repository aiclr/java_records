package cn.aiclr.jvm.designpattern.behavior.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 算法
 */
public interface Fly {
    Logger log = LoggerFactory.getLogger(Fly.class);

    void fly();
}
