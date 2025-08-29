package cn.aiclr.jvm.designpattern.structural.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>类适配器
 * 将被适配者 {@link Volatage220v} 转换产品实际需求的规格 - 5V {@link Volatage5V}
 *
 * 不适用于需要继承其他类的情况，考虑使用对象适配器
 */
public class AdapterClass extends Volatage220v implements Volatage5V {

    private static final Logger log = LoggerFactory.getLogger(AdapterClass.class);

    @Override
    public int outPut5V() {
        int v = super.output220v() / 44;
        log.info("经过适配器电压转换为：{}v", v);
        return v;
    }
}
