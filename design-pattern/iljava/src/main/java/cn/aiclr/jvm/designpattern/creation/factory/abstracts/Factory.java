package cn.aiclr.jvm.designpattern.creation.factory.abstracts;

import cn.aiclr.jvm.designpattern.creation.factory.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义抽象接口
 */
public interface Factory {

    Logger log = LoggerFactory.getLogger(Factory.class);

    /**
     * 定义抽象方法
     *
     * @param pizzaType
     * @return
     */
    Pizza createPizza(String pizzaType);
}
