package cn.aiclr.jvm.suggestions.book.java151.chapter07.ec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 定义抽象能力
 */
public interface Feature {

    Logger logger = LoggerFactory.getLogger(Feature.class);

    /**
     * 加载能力
     */
    void load();

}
