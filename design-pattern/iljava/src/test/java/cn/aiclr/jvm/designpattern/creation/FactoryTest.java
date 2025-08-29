package cn.aiclr.jvm.designpattern.creation;

import cn.aiclr.jvm.designpattern.creation.factory.BJCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.BJGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.LDCheesePizza;
import cn.aiclr.jvm.designpattern.creation.factory.LDGreekPizza;
import cn.aiclr.jvm.designpattern.creation.factory.Pizza;
import cn.aiclr.jvm.designpattern.creation.factory.abstracts.FactoryBJEnum;
import cn.aiclr.jvm.designpattern.creation.factory.abstracts.FactoryLDEnum;
import cn.aiclr.jvm.designpattern.creation.factory.abstracts.OrderPizzaAbstract;
import cn.aiclr.jvm.designpattern.creation.factory.method.FactoryBJ;
import cn.aiclr.jvm.designpattern.creation.factory.method.FactoryLD;
import cn.aiclr.jvm.designpattern.creation.factory.method.OrderPizza;
import cn.aiclr.jvm.designpattern.creation.factory.simple.Factory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-ValueSource">@ValueSource</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-sources-null-and-empty">@NullAndEmptySource</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests-argument-aggregation">@CsvSource</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-nested">@Nested</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-test-instance-lifecycle">@TestInstance</a>
 * @see <a href="https://junit.org/junit5/docs/current/user-guide/#writing-tests-classes-and-methods">@BeforeAll</a>
 */
@DisplayName("工厂模式")
class FactoryTest {

    @DisplayName("静态工厂模式-正常参数")
    @ParameterizedTest
    @ValueSource(strings = {"BJCheesePizza", "BJGreekPizza", "LDCheesePizza", "LDGreekPizza"})
    void staticFactory(String pizzaType) {
        Pizza pizza = Factory.staticFactory(pizzaType);
        assertInstanceOf(Pizza.class, pizza);
    }

    @DisplayName("静态工厂模式-异常参数")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"nopizza"})
    void staticFactoryNULL(String pizzaType) {
        assertNull(Factory.staticFactory(pizzaType));
    }

    @DisplayName("简单工厂模式-正常参数")
    @ParameterizedTest
    @ValueSource(strings = {"BJCheesePizza", "BJGreekPizza", "LDCheesePizza", "LDGreekPizza"})
    void simpleFactory(String pizzaType) {
        Pizza pizza = Factory.getInstance().simpleFactory(pizzaType);
        assertInstanceOf(Pizza.class, pizza);
    }

    @DisplayName("简单工厂模式-正常参数")
    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"nopizza"})
    void simpleFactoryNULL(String pizzaType) {
        assertNull(Factory.getInstance().simpleFactory(pizzaType));
    }

    @DisplayName("抽象工厂模式-正常参数")
    @ParameterizedTest
    @CsvSource({
            "Cheese, BJ",
            "Greek, BJ",
            "Cheese, LD",
            "Greek, LD"
    })
    void abstractFactory(String pizzaType, String factoryType) {
        OrderPizzaAbstract order = new OrderPizzaAbstract(factoryType);
        assertTrue(order.getFactory() instanceof FactoryLDEnum || order.getFactory() instanceof FactoryBJEnum);
        assertInstanceOf(Pizza.class, order.getPizza(pizzaType));
    }

    @DisplayName("工厂方法模式")
    @Nested
    @TestInstance(TestInstance.Lifecycle.PER_CLASS)
    class MethodFactory {

        OrderPizza orderBJ;
        OrderPizza orderLD;

        @DisplayName("工厂方法模式-获得工厂-正常参数")
        @Test
        @BeforeAll
        void factoryTest() {
            orderBJ = new OrderPizza("BJ");
            orderLD = new OrderPizza("LD");
            assertInstanceOf(FactoryBJ.class, orderBJ.getFactory());
            assertInstanceOf(FactoryLD.class, orderLD.getFactory());
        }

        @DisplayName("工厂方法模式-生产产品-正常参数")
        @Test
        void pizzaTest() {
            assertInstanceOf(LDGreekPizza.class, orderLD.getPizza("Greek"));
            assertInstanceOf(LDCheesePizza.class, orderLD.getPizza("Cheese"));
            assertInstanceOf(BJGreekPizza.class, orderBJ.getPizza("Greek"));
            assertInstanceOf(BJCheesePizza.class, orderBJ.getPizza("Cheese"));
        }
    }
}
