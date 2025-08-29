package cn.aiclr.jvm.designpattern.structural.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <pre>对象适配器
 * 将被适配者{@link Volatage220v} 转换产品实际需求的规格 - 5V {@link Volatage5V}
 */
public class AdapterObject implements Volatage5V {

    private static final Logger log = LoggerFactory.getLogger(AdapterObject.class);

    private final Volatage220v source;

    public AdapterObject(Volatage220v source) {
        this.source = source;
    }

    @Override
    public int outPut5V() {
        int v = source.output220v() / 44;
        log.info("经过适配器电压转换为：" + v + "v");
        return v;
    }
}
