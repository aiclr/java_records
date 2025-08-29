package cn.aiclr.jvm.designpattern.structural.proxy.proxycglib;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 被代理类 不需要是接口
 */
public class Dog {

    private static final Logger log = LoggerFactory.getLogger(Dog.class);

    public String bark() {
        log.info("{} Dog is barking", this.getClass().getSimpleName());
        return "汪汪";
    }

    /**
     * <pre>无法代理增强 static 方法
     * cglib 无法设置回调函数 {@code  enhancer.setCallback(this);}
     * 因为 static 方法的 local variables 中不存在 this
     */
    public static String bark2() {
        log.info("static method is running");
        return "static";
    }

    /**
     * <pre>
     * final 无法被代理
     * final 无法被子类重写
     */
    public final String bark3() {
        log.info("{} final method is running", this.getClass().getSimpleName());
        return "final";
    }
}
