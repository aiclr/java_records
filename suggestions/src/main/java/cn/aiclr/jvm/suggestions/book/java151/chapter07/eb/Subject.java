package cn.aiclr.jvm.suggestions.book.java151.chapter07.eb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象主题角色
 */
public interface Subject {

    Logger logger = LoggerFactory.getLogger(Subject.class);

    /**
     * 方法
     */
    void request();
}
