package cn.aiclr.jvm.designpattern.behavior.observer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>观察者模式 ocp 原则
 * 对象之间多对一依赖的一种方案,被依赖对象为 {@link Subject},依赖对象为 {@link Observer}
 * {@link Subject} 通知 {@link Observer}
 *
 * 以集合方式管理 {@link Observer}: 注册,移除,通知
 * 增加具体 {@link Observer} 不需要修改核心 {@link Subject} 实现类 {@link SubjectWeather}
 * 遵循 OCP 原则
 */
public interface Observer {

    Logger log = LoggerFactory.getLogger(Observer.class);

    void update(String data);
}
