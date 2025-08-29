package cn.aiclr.jvm.designpattern.creation.factory.abstracts;

import cn.aiclr.jvm.designpattern.creation.factory.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>抽象工厂模式
 *
 * 定义接口{@link Factory}，
 * 定义创建对象的方法{@link Factory#createPizza(String)}，
 * 由具体子类 {@link FactoryLDEnum}、{@link FactoryBJEnum} 决定要实例化的产品对象。
 *
 * {@link OrderPizzaAbstract}建造者模式 中 指挥者角色
 */
public class OrderPizzaAbstract {

    private static final Logger log = LoggerFactory.getLogger(OrderPizzaAbstract.class);

    Factory factory;

    public OrderPizzaAbstract(String factoryType) {
        if (factoryType == null || factoryType.equals("")) {
            log.error("请选择正确工厂");
            return;
        }
        switch (factoryType) {
            case "BJ":
                factory = FactoryBJEnum.INSTANCE;
                break;
            case "LD":
                factory = FactoryLDEnum.INSTANCE;
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
