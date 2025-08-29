package cn.aiclr.jvm.designpattern.behavior.template.soyamilk;

/**
 * 红豆豆浆
 */
public class SoyaMilkRedBean extends SoyaMilk {
    @Override
    public void addCondiments() {
        log.info("加入红豆");
    }
}
