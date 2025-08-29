package cn.aiclr.jvm.designpattern.creation.factory.method;

import cn.aiclr.jvm.designpattern.creation.factory.BJCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.BJGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.Pizza;

public class FactoryBJ extends Factory {

    private FactoryBJ() {
    }

    //静态内部类实现单例
    private static class FactoryBJInner {
        private static final FactoryBJ INSTANCE = new FactoryBJ();
    }

    public static FactoryBJ getInstance() {
        return FactoryBJInner.INSTANCE;
    }


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
