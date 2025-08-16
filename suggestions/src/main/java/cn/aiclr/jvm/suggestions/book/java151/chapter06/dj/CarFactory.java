package cn.aiclr.jvm.suggestions.book.java151.chapter06.dj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 经典工厂方法
 */
public class CarFactory {

    private static final Logger logger = LoggerFactory.getLogger(CarFactory.class);

    public static Car createCar(Class<? extends Car> c) {
        try {
            return c.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            logger.error("{}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }
}
