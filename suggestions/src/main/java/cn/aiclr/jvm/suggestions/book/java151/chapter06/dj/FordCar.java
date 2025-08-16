package cn.aiclr.jvm.suggestions.book.java151.chapter06.dj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FordCar implements Car {

    private static final Logger logger = LoggerFactory.getLogger(FordCar.class);

    @Override
    public void display() {
        logger.info("FordCar");
    }
}
