package cn.aiclr.jvm.designpattern.structural;

import cn.aiclr.jvm.designpattern.structural.decorator.CaffeDecaf;
import cn.aiclr.jvm.designpattern.structural.decorator.CaffeLongBlack;
import cn.aiclr.jvm.designpattern.structural.decorator.DecoratorChocolate;
import cn.aiclr.jvm.designpattern.structural.decorator.DecoratorMilk;
import cn.aiclr.jvm.designpattern.structural.decorator.Drink;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@DisplayName("装饰者模式")
class DecoratorTest {

    private static final Logger log = LoggerFactory.getLogger(DecoratorTest.class.getSimpleName());

    @Test
    void decoratorTest() {
        Drink drink = new CaffeDecaf();
        log.info(drink.getDes());
        Assertions.assertEquals(4.0f, drink.cost());

        //加一份牛奶
        drink = new DecoratorMilk(drink);
        log.info(drink.getDes());
        Assertions.assertEquals(6.0f, drink.cost());

        //再加一份牛奶
        drink = new DecoratorMilk(drink);
        log.info(drink.getDes());
        Assertions.assertEquals(8.0f, drink.cost());

        //加一份巧克力
        drink = new DecoratorChocolate(drink);
        log.info(drink.getDes());
        Assertions.assertEquals(13.0f, drink.cost());

        drink = new CaffeLongBlack();
        log.info(drink.getDes());
        Assertions.assertEquals(10.0f, drink.cost());

    }
}
