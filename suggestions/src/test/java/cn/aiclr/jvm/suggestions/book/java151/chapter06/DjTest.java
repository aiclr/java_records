package cn.aiclr.jvm.suggestions.book.java151.chapter06;

import cn.aiclr.jvm.suggestions.book.java151.chapter06.dj.Car;
import cn.aiclr.jvm.suggestions.book.java151.chapter06.dj.CarFactory;
import cn.aiclr.jvm.suggestions.book.java151.chapter06.dj.EnumAbstractCarFactory;
import cn.aiclr.jvm.suggestions.book.java151.chapter06.dj.EnumCarFactory;
import cn.aiclr.jvm.suggestions.book.java151.chapter06.dj.FordCar;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class DjTest {
    @Test
    void testFactory() {
        Car car = CarFactory.createCar(FordCar.class);
        car.display();
        Assertions.assertThrows(RuntimeException.class, () -> CarFactory.createCar(Car.class).display());
    }

    @Test
    void testEnumFactory() {
        Car car = EnumCarFactory.BuickCar.create();
        car.display();
    }

    @Test
    void testEnumAbstractFactory() {
        Car car = EnumAbstractCarFactory.BuickCar.create();
        car.display();
    }
}
