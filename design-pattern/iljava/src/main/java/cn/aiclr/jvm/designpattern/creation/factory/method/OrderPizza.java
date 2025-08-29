package cn.aiclr.jvm.designpattern.creation.factory.method;

import cn.aiclr.jvm.designpattern.creation.factory.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>工厂方法模式
 *
 * 定义抽象类 {@link Factory}，
 * 定义创建对象的抽象方法 {@link Factory#createPizza(String)}，
 * 由具体子类 {@link FactoryLD}、{@link FactoryBJ} 决定要实例化的产品对象。
 *
 * {@link OrderPizza}建造者模式 中 指挥者角色
 */
public class OrderPizza {

    private static final Logger log = LoggerFactory.getLogger(OrderPizza.class);

    private Factory factory;

    public OrderPizza(String factoryType) {
        if (factoryType == null || factoryType.isEmpty()) {
            log.error("请选择正确工厂");
            return;
        }
        switch (factoryType) {
            case "BJ":
                factory = FactoryBJ.getInstance();
                break;
            case "LD":
                factory = FactoryLD.getInstance();
                break;
            default:
                log.error("{} is unknown!", factoryType);
                break;
        }
    }

    public Pizza getPizza(String pizzaType) {
        return factory == null ? null : factory.createPizza(pizzaType);
    }

    public Factory getFactory() {
        return factory;
    }
}
