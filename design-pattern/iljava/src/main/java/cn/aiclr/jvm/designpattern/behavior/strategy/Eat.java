package cn.aiclr.jvm.designpattern.behavior.strategy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>算法 - 策略
 *
 * 如果策略带返回值 可以使用 lambda 简化 策略
 */
public interface Eat {
    Logger log = LoggerFactory.getLogger(Eat.class);

    String eat();
}
