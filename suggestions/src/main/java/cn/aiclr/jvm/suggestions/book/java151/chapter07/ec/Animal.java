package cn.aiclr.jvm.suggestions.book.java151.chapter07.ec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义主角
 */
public interface Animal {

    Logger logger = LoggerFactory.getLogger(Animal.class);

    void doStuff();
}
