package cn.aiclr.jvm.designpattern.structural.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 被适配者 - 当前拥有的电源
 */
public class Volatage220v {

    private static final Logger log = LoggerFactory.getLogger(Volatage220v.class);

    public int output220v() {
        log.info("当前电源输出电压220v");
        return 220;
    }
}
