package cn.aiclr.jvm.designpattern.structural.proxy.proxydynamic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 必须有接口才能使用 jdk 动态代理
 */
public interface Student {
    Logger log = LoggerFactory.getLogger(Student.class.getSimpleName());

    void study();

    String ask(String question);
}
