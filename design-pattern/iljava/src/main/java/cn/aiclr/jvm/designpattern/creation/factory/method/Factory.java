package cn.aiclr.jvm.designpattern.creation.factory.method;

import cn.aiclr.jvm.designpattern.creation.factory.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义抽象类
 */
public abstract class Factory {
    protected static final Logger log = LoggerFactory.getLogger(Factory.class);

    /**
     * 定义抽象方法
     *
     * @param pizzaType
     * @return
     */
    public abstract Pizza createPizza(String pizzaType);
}
