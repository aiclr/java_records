package cn.aiclr.jvm.designpattern.creation.factory.simple;

import cn.aiclr.jvm.designpattern.creation.factory.BJCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.BJGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.LDCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.LDGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.Pizza;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * <pre>ocp原则，单一职责原则
 *
 * 当需求变化，比如增加一个Pizza种类时候，只需要修改{@link Factory#getPizza(String)}。符合 ocp 原则
 *
 * 单例工厂+简单工厂模式 {@link #simpleFactory(String)}
 * 静态工厂模式 {@link #staticFactory(String)}
 *
 * 简单工厂一般配合单例模式使用
 */
public class Factory {

    private static final Logger log = LoggerFactory.getLogger(Factory.class.getSimpleName());

    private Factory() {
    }

    private static volatile Factory INSTANCE;

    //双重检查 实现单例模式
    public static Factory getInstance() {
        if (INSTANCE == null) {
            synchronized (Factory.class) {
                if (INSTANCE == null)
                    INSTANCE = new Factory();
            }
        }
        return INSTANCE;
    }


    /**
     * <pre>单例模式+简单工厂模式
     *
     * @param pizzaType pizza type
     * @return Pizza
     */
    public Pizza simpleFactory(String pizzaType) {
        return getPizza(pizzaType);
    }


    /**
     * <pre>静态工厂模式
     *
     * @param pizzaType pizza type
     * @return Pizza
     */
    public static Pizza staticFactory(String pizzaType) {
        return getPizza(pizzaType);
    }

    /**
     * <pre>抽离公用代码
     * @param pizzaType pizza type
     * @return Pizza
     */
    private static Pizza getPizza(String pizzaType) {
        if (pizzaType == null || pizzaType.isEmpty()) {
            return null;
        }
        Pizza pizza;
        switch (pizzaType) {
            case "BJCheesePizza":
                pizza = new BJCheesePizza();
                break;
            case "BJGreekPizza":
                pizza = new BJGreekPizza();
                break;
            case "LDCheesePizza":
                pizza = new LDCheesePizza();
                break;
            case "LDGreekPizza":
                pizza = new LDGreekPizza();
                break;
            default:
                log.error(pizzaType + " order failure!");
                return null;
        }
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
