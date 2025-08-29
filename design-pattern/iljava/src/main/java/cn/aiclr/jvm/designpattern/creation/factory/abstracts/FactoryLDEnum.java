package cn.aiclr.jvm.designpattern.creation.factory.abstracts;

import cn.aiclr.jvm.designpattern.creation.factory.LDCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.LDGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.Pizza;

/**
 * 枚举实现单例模式
 */
public enum FactoryLDEnum implements Factory {

    INSTANCE;

    @Override
    public Pizza createPizza(String pizzaType) {
        if (pizzaType == null || pizzaType.isEmpty()) {
            return null;
        }
        Pizza pizza;
        switch (pizzaType) {
            case "Cheese":
                pizza = new LDCheesePizza();
                break;
            case "Greek":
                pizza = new LDGreekPizza();
                break;
            default:
                log.error("{} order failure!", pizzaType);
                return null;
        }
        pizza.prepare();
        pizza.bake();
        pizza.cut();
        pizza.box();
        return pizza;
    }
}
