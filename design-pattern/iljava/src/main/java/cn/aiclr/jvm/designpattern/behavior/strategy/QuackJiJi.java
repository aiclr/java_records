package cn.aiclr.jvm.designpattern.behavior.strategy;

public class QuackJiJi implements Quack {
    @Override
    public void quack() {
        log.info("唧唧叫");
    }
}
