package cn.aiclr.jvm.designpattern.creation.factory.method;

import cn.aiclr.jvm.designpattern.creation.factory.LDCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.LDGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.Pizza;

public class FactoryLD extends Factory {
    private FactoryLD() {

    }

    private static final FactoryLD INSTANCE;

    /**
     * 静态代码块实现单例
     */
    static {
        INSTANCE = new FactoryLD();
    }

    public static FactoryLD getInstance() {
        return INSTANCE;
    }


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
