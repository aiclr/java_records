package cn.aiclr.jvm.designpattern.behavior.strategy;

public class QuackNo implements Quack {
    @Override
    public void quack() {
        log.info("不会叫");
    }
}
