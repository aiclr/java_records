package cn.aiclr.jvm.suggestions.reflex;

import cn.aiclr.jvm.suggestions.reflex.annotation.MyAfter;
import cn.aiclr.jvm.suggestions.reflex.annotation.MyBefore;
import cn.aiclr.jvm.suggestions.reflex.annotation.MyCore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Business {

    private static final Logger logger = LoggerFactory.getLogger(Business.class);

    @MyBefore
    public void init() {
        logger.info("初始化...");
    }

    @MyCore
    public void core() {
        logger.info("核心方法...");
    }

    @MyAfter
    public void destroy() {
        logger.info("销毁...");
    }
}
