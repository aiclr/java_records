package cn.aiclr.jvm.designpattern.behavior.template.soyamilk;

/**
 * <pre>纯豆浆
 * 钩子函数 {@link #hook()}
 */
public class SoyaMilkPure extends SoyaMilk {

    @Override
    public void addCondiments() {
        //do nothing 空方法
    }

    @Override
    public boolean hook() {
        return false;
    }
}
