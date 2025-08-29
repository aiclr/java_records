package cn.aiclr.jvm.designpattern.structural.flyweight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 享元模式 抽象类
 */
public abstract class FlyWeight {
    protected static final Logger log = LoggerFactory.getLogger(FlyWeight.class.getSimpleName());

    public abstract void use(StateUnshared unshared);
}
