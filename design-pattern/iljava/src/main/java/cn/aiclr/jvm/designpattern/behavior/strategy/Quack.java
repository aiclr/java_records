package cn.aiclr.jvm.designpattern.behavior.strategy;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 算法
 */
public interface Quack {
    Logger log = LoggerFactory.getLogger(Quack.class);

    void quack();
}
