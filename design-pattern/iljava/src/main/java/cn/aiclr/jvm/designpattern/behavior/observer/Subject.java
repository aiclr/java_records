package cn.aiclr.jvm.designpattern.behavior.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 订阅者
 */
public interface Subject {

    Logger log = LoggerFactory.getLogger(Subject.class);

    /**
     * 注册观察者
     */
    void registerObserver(Observer observer);

    /**
     * 移除观察者
     */
    void removeObserver(Observer observer);

    /**
     * 通知观察者
     */
    void notifyObserver();

}
