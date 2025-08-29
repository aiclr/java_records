package cn.aiclr.jvm.designpattern.structural.proxy.proxystatic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 接口
 */
public interface Teacher {

    Logger log = LoggerFactory.getLogger(Teacher.class);

    void teach();
}
