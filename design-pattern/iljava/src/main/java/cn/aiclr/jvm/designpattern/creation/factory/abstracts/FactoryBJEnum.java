package cn.aiclr.jvm.designpattern.creation.factory.abstracts;

import cn.aiclr.jvm.designpattern.creation.factory.BJCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.BJGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.Pizza;

/**
 * 枚举实现单例模式
 */
public enum FactoryBJEnum implements Factory {

    INSTANCE;

    @Override
    public Pizza createPizza(String pizzaType) {
        if (pizzaType == null || pizzaType.isEmpty()) {
            return null;
        }
        Pizza pizza;
        switch (pizzaType) {
            case "Cheese":
                pizza = new BJCheesePizza();
                break;
            case "Greek":
                pizza = new BJGreekPizza();
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
