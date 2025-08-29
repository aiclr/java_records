package cn.aiclr.jvm.designpattern.behavior.command;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>命令接受者-电灯开关
 * 命令接收者,知道如何实施和执行一个请求相关的操作
 */
public class ReceiverLight {

    private static final Logger log = LoggerFactory.getLogger(ReceiverLight.class);

    public void on() {
        log.info("电灯打开");
    }

    public void off() {
        log.info("电灯关闭");
    }
}
